package sblanco.reactive.posts.entities

data class Message(
    val id:Int,
    val userId:Int,
    val title:String,
    val body:String
)

data class User(
    val id:Int,
    val name:String,
    val userName:String
)