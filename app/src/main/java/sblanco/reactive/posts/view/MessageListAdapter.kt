package sblanco.reactive.posts.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_list_item.view.*
import sblanco.reactive.posts.R
import sblanco.reactive.posts.entities.Message

class MessageListAdapter(val onclickListener: (Message) -> Unit) : RecyclerView.Adapter<MessageListAdapter.MessageListItemHolder>() {

    private var mMessages:MutableList<Message> = arrayListOf()

    fun updateMessages(newCustomers:List<Message>){
        mMessages.clear()
        mMessages.addAll(newCustomers)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MessageListAdapter.MessageListItemHolder {
        return MessageListItemHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.message_list_item, viewGroup, false))
    }

    override fun onBindViewHolder(holder: MessageListAdapter.MessageListItemHolder, position: Int) = holder.bind( mMessages[position],onclickListener)

    override fun getItemCount(): Int {
        return mMessages.size
    }

    class MessageListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(message: Message, onclickListener: (Message) -> Unit)= with(itemView){
            message_title.text = message.title
            message_body.text = message.body

            setOnClickListener { onclickListener(message) }
        }
    }
}