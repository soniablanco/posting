package sblanco.reactive.posts.model.network.retrofit

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.entities.User
import sblanco.reactive.posts.model.network.MessagesService

class RetrofitMessagesService:MessagesService {
    private val placeHolderApi: JsonPlaceHolderService = ServiceGenerator.createService(
            JsonPlaceHolderService::class.java)

    override fun getMessages(): Observable<List<Message>> {
        /*return Observable.create<List<Message>> { emiter ->
            val call = placeHolderApi.getMessages()
            call.enqueue(object : Callback<List<Message>> {
                override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                    if (response.isSuccessful) {
                        emiter.onNext(response.body()!!)
                        emiter.onComplete()
                    } else {
                        emiter.onError(Throwable(response.message()))
                    }
                }

                override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    emiter.onError(t)
                }

            })
        }*/
        return getFromService{
            placeHolderApi.getMessages()
        }
    }

    override fun getUsers(): Observable<List<User>> {
        return getFromService{
            placeHolderApi.getUsers()
        }
    }

    private fun<T> getFromService(path:(Unit)->Call<T>):Observable<T>{
        return Observable.create<T>{ emiter ->
            val call = path(Unit)
            call.enqueue(object:Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        emiter.onNext(response.body()!!)
                        emiter.onComplete()
                    } else {
                        emiter.onError(Throwable(response.message()))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    emiter.onError(t)
                }
            })
        }
    }
}