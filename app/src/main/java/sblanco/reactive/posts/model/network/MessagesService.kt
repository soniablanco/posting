package sblanco.reactive.posts.model.network

import io.reactivex.Observable
import sblanco.reactive.posts.entities.Message

interface MessagesService {
    fun getMessages():Observable<List<Message>>
}