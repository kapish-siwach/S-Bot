package com.example.sbot

data class ChatItem(
    val text:String="",
    val userType:UserType
)
enum class UserType {
    USER, AI
}
