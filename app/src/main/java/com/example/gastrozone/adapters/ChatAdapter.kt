package com.example.gastrozone.adapters

import android.content.Context
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gastrozone.R
import org.json.JSONObject

class ChatAdapter(val context : Context, val chatList : ArrayList<JSONObject>)
    : RecyclerView.Adapter<ChatAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view : View? = null

        view = LayoutInflater.from(context).inflate(R.layout.list_messages,parent,false)

        return ViewHolder(view!!)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content = chatList[position]
        holder.message.text = content["message"].toString()
        holder.userName.text = content["user"].toString()
    }


    class ViewHolder(itemView : View):  RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById<TextView>(R.id.textName)
        val message = itemView.findViewById<TextView>(R.id.textMessage)
    }

}
