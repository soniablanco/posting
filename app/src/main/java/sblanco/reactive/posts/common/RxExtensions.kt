package sblanco.reactive.posts.common

import android.widget.Button
import io.reactivex.Observable

fun Button.toObservable():Observable<Any>{
    val root= this
    return Observable.create<Any>{
            o ->
        root.setOnClickListener {
            o.onNext(it)
        }
    }
}

