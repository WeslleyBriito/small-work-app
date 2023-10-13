package com.opensystem.smallwork.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.R
import com.opensystem.smallwork.models.Chat

class ChatAdapter(val chatList: List<Chat>, private val clickListener: (Chat) -> Unit)
    : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_chat, parent, false))
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.itemView.setOnClickListener { clickListener(Chat()) }
    }

}