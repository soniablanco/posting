package sblanco.reactive.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import sblanco.reactive.posts.common.toObservable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refresh_btn.toObservable()
            .subscribe{
                Log.d("Click","it worked")
            }
    }
}
