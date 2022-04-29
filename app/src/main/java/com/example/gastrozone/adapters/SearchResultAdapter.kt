package com.example.gastrozone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gastrozone.R
import kotlinx.android.synthetic.main.list_item.view.*

class SearchResultAdapter : RecyclerView.Adapter<SearchResultAdapter.XdrHolder>() {
    private var usernamesList = ArrayList<String>()
    private var objectIDsList = ArrayList<String>()
    fun setNewData(usernames: ArrayList<String>, objectIDs: ArrayList<String>) {
        usernamesList = usernames
        objectIDsList = objectIDs
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XdrHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return XdrHolder(view)
    }
    override fun getItemCount(): Int {
        return usernamesList.size
    }
    override fun onBindViewHolder(holder: XdrHolder, position: Int) {
        holder.bindData(usernamesList[position],objectIDsList[position])
    }
    class XdrHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(username: String,objectID:String) {
            itemView.menoPodniku.text = username
        }
    }
}