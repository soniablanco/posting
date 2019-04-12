package sblanco.reactive.posts.entities

data class Message(
    val id:Int,
    val userId:Int,
    val title:String,
    val body:String
)