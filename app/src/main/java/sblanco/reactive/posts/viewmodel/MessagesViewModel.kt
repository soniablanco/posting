package sblanco.reactive.posts.viewmodel

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.get
import sblanco.reactive.posts.model.network.MessagesService
import sblanco.reactive.posts.view.MessageListAdapter


class MessagesViewModel: KoinComponent {

    private val messagesService:MessagesService = get()

    var messageListAdapter = MessageListAdapter{

    }

    private var disposables: CompositeDisposable = CompositeDisposable()

    fun getMessages(initialEvents:Observable<Any>) {

         disposables.add(initialEvents
             .flatMap {
                messagesService.getMessages()
             }
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe{
                messageListAdapter.updateMessages(it)
                messageListAdapter.notifyDataSetChanged()
                Log.d("Click",it!!.size.toString())

            })
    }

    fun getUsers(initialEvents:Observable<Any>){
        disposables.add(initialEvents
            .flatMap {
                messagesService.getUsers()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("Users",it!!.size.toString())
            })
    }

    fun clear(){
        if (!disposables.isDisposed) disposables.dispose()
    }
}