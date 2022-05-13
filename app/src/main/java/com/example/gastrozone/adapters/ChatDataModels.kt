package com.example.gastrozone.adapters

class ChatDataModels {
    data class Messages (val messageContent : String)
    data class initialData (val userName : String, val roomName : String)
    data class SendMessage(val messageContent: String)
}