package com.example.wgpchat.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "history_chat")
class Chat {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    var id: Int = 0

    @ColumnInfo(name = "_request")
    var request: String = ""

    @ColumnInfo(name="_response")
    var response: String = ""

    @ColumnInfo(name = "_chatDate")
    var chatDate: String = ""

    @ColumnInfo(name = "_isFavorite")
    var isFavorite: Boolean = false

    constructor(){}

    constructor(id:Int, request:String, response:String, chatDate:String, isFavorite:Boolean){
        this.request = request
        this.response = response
        this.chatDate = chatDate
        this.isFavorite = isFavorite
    }

    constructor(request:String, response:String,chatDate: String,isFavorite:Boolean){
        this.request = request
        this.response = response
        this.chatDate = chatDate
        this.isFavorite = isFavorite
    }
}