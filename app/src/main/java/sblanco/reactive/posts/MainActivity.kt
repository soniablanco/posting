package sblanco.reactive.posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.message_list_item.view.*
import sblanco.reactive.posts.common.toObservable
import sblanco.reactive.posts.entities.Message

class MainActivity : AppCompatActivity() {

    private val model = MessagesModel()

    private val bag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messages_list.layoutManager = LinearLayoutManager(this)
        messages_list.addItemDecoration(DividerItemDecoration( messages_list.context, DividerItemDecoration.VERTICAL))

        messages_list.adapter = MessageListItemAdapter{

        }

        bag.add(
            model.getMessages(refresh_btn.toObservable().startWith(this))
            .subscribe{
                messagesAdapter.updateMessages(it)
                messagesAdapter.notifyDataSetChanged()
                Log.d("Click",it!!.size.toString())
            }
        )
    }


    private val messagesAdapter get() = messages_list.adapter as MessageListItemAdapter

    private inner class MessageListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: Message, onclickListener: (Message) -> Unit)= with(itemView){
            message_title.text = message.title
            message_body.text = message.body

            setOnClickListener { onclickListener(message) }
        }
    }

    private inner class MessageListItemAdapter(val onclickListener: (Message) -> Unit) : RecyclerView.Adapter<MessageListItemHolder>() {
        private var mMessages:MutableList<Message> = arrayListOf()

        fun updateMessages(newCustomers:List<Message>){
            mMessages.clear()
            mMessages.addAll(newCustomers)
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageListItemHolder {
            return MessageListItemHolder(LayoutInflater.from(this@MainActivity).inflate(R.layout.message_list_item, viewGroup, false))
        }

        override fun onBindViewHolder(holder: MessageListItemHolder, position: Int) = holder.bind( mMessages[position],onclickListener)

        override fun getItemCount(): Int {
            return mMessages.size
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }
}
