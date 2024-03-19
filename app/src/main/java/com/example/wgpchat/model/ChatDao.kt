package com.example.wgpchat.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface ChatDao {

    @Insert
    fun insertChat(chat:Chat)

    @Query("SELECT * FROM history_chat WHERE _id = :id")
    fun findRequest(id:Int):List<Chat>

    @Query("DELETE FROM history_chat WHERE _id = :id")
    fun deleteReq(id:Int)

    @Query("SELECT * FROM history_chat")
    fun getAllRequests(): List<Chat>

    @Query("SELECT * FROM history_chat WHERE _isFavorite= :isFavorite")
    fun getAllFavorites(isFavorite:Boolean): List<Chat>

    @Query("UPDATE history_chat SET _isFavorite  = :isFavorite  WHERE _id = :id")
    fun updateFavorite(id:Int, isFavorite: Boolean)


}


