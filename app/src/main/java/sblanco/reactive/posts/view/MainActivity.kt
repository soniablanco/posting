package sblanco.reactive.posts.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

import sblanco.reactive.posts.viewmodel.MessagesViewModel
import sblanco.reactive.posts.R
import sblanco.reactive.posts.common.toObservable
import sblanco.reactive.posts.model.network.retrofit.RetrofitMessagesService

class MainActivity : AppCompatActivity() {

    private val model = MessagesViewModel(RetrofitMessagesService())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messages_list.layoutManager = LinearLayoutManager(this)
        messages_list.addItemDecoration(DividerItemDecoration( messages_list.context, DividerItemDecoration.VERTICAL))

        messages_list.adapter = model.messageListAdapter
        model.getMessages(refresh_btn.toObservable().startWith(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        model.clear()
    }
}
