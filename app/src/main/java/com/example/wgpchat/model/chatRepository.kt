package com.example.wgpchat.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key.Companion.T
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException


class ChatRepository(private val chatDao: ChatDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertChat(newChat:Chat){
        coroutineScope.launch (Dispatchers.IO ) { chatDao.insertChat(newChat)}
    }

    fun deleteChat(Id:Int){
        coroutineScope.launch (Dispatchers.IO ) { chatDao.deleteReq(Id)}
    }

    fun allRequest(){
        coroutineScope.launch (Dispatchers.Main ) { getAllChats() }
    }

    fun getFavorites(fav:Boolean){
        coroutineScope.launch (Dispatchers.IO ) { chatDao.getAllFavorites(fav)}
    }

    fun updateFavorite(id:Int,isFavorite:Boolean){
        coroutineScope.launch (Dispatchers.IO ) { chatDao.updateFavorite(id,isFavorite)}
    }





    private suspend fun loadChats(): List<Chat> {
        return withContext(Dispatchers.IO) {
            try {
                chatDao.getAllRequests()
            } catch (e: Exception) {
                when (e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if (chatDao.getAllRequests().isEmpty())
                            throw Exception(
                                "Something went wrong. " +
                                        "We have no data.")
                    }
                    else -> throw e
                }
            }
            return@withContext chatDao.getAllRequests()!!
        }
    }

    suspend fun  getAllChats():List<Chat>{
        return withContext(Dispatchers.IO){
            return@withContext chatDao.getAllRequests()
        }
    }

}


