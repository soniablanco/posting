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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refresh_btn.toObservable()
            .flatMap {
                val result = Observable.create<Response<List<Message>>>{
                    emiter ->
                    val call =ServiceGenerator.createService(JsonPlaceHolderService::class.java).getMessages()
                    call.enqueue(object: Callback<List<Message>> {
                        override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                            if(response.isSuccessful) {
                                emiter.onNext(response)
                                emiter.onComplete()
                            }
                            else{
                                emiter.onError(Throwable(response.message()))
                            }
                        }
                        override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                            emiter.onError(t)
                        }

                    })
                }
                return@flatMap result
            }
            .map {
                it.body()
            }
            .subscribe{
                Log.d("Click",it!!.size.toString())
            }
    }
}
