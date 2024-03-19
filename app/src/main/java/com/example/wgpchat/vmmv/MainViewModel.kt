package com.example.wgpchat.vmmv


import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wgpchat.model.Chat
import com.example.wgpchat.model.ChatDatabase
import com.example.wgpchat.model.ChatRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application):ViewModel(){


     private val _allRequestsList = mutableStateListOf<Chat>()

     var errorMessage = mutableStateOf("")

     val allRequestsList:List<Chat>
         get() = _allRequestsList



     private val repository:ChatRepository


    init {
        val chatDb = ChatDatabase.getInstance(application)
        val chatDao = chatDb.chatDao()
         repository = ChatRepository(chatDao)

         getChatRequestList()

    }


    fun getChatRequestList(){
        viewModelScope.launch {
            val requests = repository.getAllChats()

            try{
                _allRequestsList.clear()
                _allRequestsList.addAll(requests)
                Log.d("[pinga]", "hay datos para aca ${requests.size}")
            }catch (e:Exception){
                errorMessage.value = e.message.toString()
            }
        }
    }





    fun insertChat(chat:Chat){
        repository.insertChat(chat)
    }

    fun findChat(req:Int){
      //  repository.findRequest(req)
    }

    suspend  fun getAllChats():List<Chat>{
        return repository.getAllChats()
    }

    fun deleteChat(req:Int){
       repository.deleteChat(req)
    }

    fun updateFavorite(id:Int, isFavorite:Boolean){
        repository.updateFavorite(id,isFavorite)
    }

}