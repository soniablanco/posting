package sblanco.reactive.posts

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.network.JsonPlaceHolderService
import sblanco.reactive.posts.network.ServiceGenerator

class MessagesModel {
    private val placeHolderApi: JsonPlaceHolderService

    init {
        placeHolderApi = ServiceGenerator.createService(JsonPlaceHolderService::class.java)
    }

    fun getMessages(initialEvents:Observable<Any>): Observable<List<Message>> {
        return initialEvents.flatMap {
            getMessagesFromService()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getMessagesFromService(): Observable<List<Message>> {
        return Observable.create<List<Message>> { emiter ->
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
        }
    }
}