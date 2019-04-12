package sblanco.reactive.posts.common

import android.view.View
import android.widget.Button
import io.reactivex.Observable

fun Button.toObservable():Observable<View>{
    val root= this
    return Observable.create<View>{
            o ->
        root.setOnClickListener {
            o.onNext(it)
        }
    }
}

