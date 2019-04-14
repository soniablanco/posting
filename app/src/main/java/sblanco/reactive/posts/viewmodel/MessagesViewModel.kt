package sblanco.reactive.posts.viewmodel

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.model.network.JsonPlaceHolderService
import sblanco.reactive.posts.model.network.ServiceGenerator
import sblanco.reactive.posts.view.MessageListAdapter

class MessagesModel {
    private val placeHolderApi: JsonPlaceHolderService

    var messageListAdapter = MessageListAdapter{

    }

    private lateinit var disposables: CompositeDisposable

    init {
        initRx()
        placeHolderApi = ServiceGenerator.createService(JsonPlaceHolderService::class.java)
    }

    private fun initRx(){
        disposables = CompositeDisposable()
    }

    fun getMessages(initialEvents:Observable<Any>) {

         disposables.add(initialEvents
             .flatMap {
                getMessagesFromService()
             }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe{
                messageListAdapter.updateMessages(it)
                messageListAdapter.notifyDataSetChanged()
                Log.d("Click",it!!.size.toString())

            })
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

    fun clear(){
        if (!disposables.isDisposed) disposables.dispose()
    }
}