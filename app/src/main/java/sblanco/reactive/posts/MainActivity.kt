package sblanco.reactive.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sblanco.reactive.posts.common.toObservable
import sblanco.reactive.posts.entities.Message
import sblanco.reactive.posts.network.JsonPlaceHolderService
import sblanco.reactive.posts.network.ServiceGenerator

class MainActivity : AppCompatActivity() {

    val model = MessagesModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refresh_btn.toObservable()
            .flatMap {
               model.getMessages()
            }
            .map {
                it.body()
            }
            .subscribe{
                Log.d("Click",it!!.size.toString())
            }
    }
}
