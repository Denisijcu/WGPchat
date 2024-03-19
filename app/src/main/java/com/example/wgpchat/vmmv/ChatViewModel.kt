package com.example.wgpchat.vmmv


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.wgpchat.models.GPTApi
import com.example.wgpchat.models.GPTReq
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ChatViewModel() : ViewModel() {

    var answer = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(GPTApi::class.java)

    suspend fun sendRequest(question: String) {
        val request = GPTReq(
            prompt = question,
            max_tokens = 1000,
            model = "gpt-3.5-turbo-instruct",
            top_p=1,
            frequency_penalty=0,
            presence_penalty=0
        )

        answer.value = ""

        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true

            var retries = 0
            while (retries < 3) {
                try {
                    val response = withContext(Dispatchers.IO) {
                        api.getCompletion(request).execute()
                    }

                    isLoading.value = false

                    if (response.isSuccessful) {
                        val choice = response.body()?.choices?.get(0)
                        viewModelScope.launch(Dispatchers.Main) {
                            choice?.text?.let {
                                answer.value = it
                            }
                        }
                        break // success, break out of loop
                    } else {
                        viewModelScope.launch(Dispatchers.Main) {
                            answer.value = "Error: ${response.code()} - ${response.message()}"
                        }
                        break // not successful, break out of loop
                    }
                } catch (e: Exception) {
                    retries++
                    if (retries >= 3) {
                        viewModelScope.launch(Dispatchers.Main) {
                            answer.value = "Error: ${e.message}. Modifiy your input and try again"
                            isLoading.value = false
                        }
                    } else {
                        delay(5000) // wait 5 seconds before retrying
                    }
                }
            }
        }
    }

}