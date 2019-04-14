package sblanco.reactive.posts.model.network

import io.reactivex.Observable
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.entities.User

interface MessagesService {
    fun getMessages():Observable<List<Message>>

    fun getUsers():Observable<List<User>>
}