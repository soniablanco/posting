package sblanco.reactive.posts.model.network.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.entities.User

interface JsonPlaceHolderService {
    @GET("/posts")
    fun getMessages(): Call<List<Message>>

    @GET("/posts/{articleId}")
    fun getMessage(@Path("articleId") articleId: String): Call<Message>

    @POST("/posts")
    fun postMessage(@Body message: Message): Call<Message>

    @GET("/users")
    fun getUsers():Call<List<User>>
}