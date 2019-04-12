package sblanco.reactive.posts.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import sblanco.reactive.posts.entities.Message

interface JsonPlaceHolderService {
    @GET("/posts")
    fun getMessages(): Call<List<Message>>

    @GET("/posts/{articleId}")
    fun getMessage(@Path("articleId") articleId: String): Call<Message>

    @POST("/posts")
    fun postMessage(@Body message: Message): Call<Message>
}