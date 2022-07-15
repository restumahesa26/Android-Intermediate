package com.example.storyapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.model.ListUserModel
import com.example.storyapp.R

class ListUserAdapter(private val listUser: ArrayList<ListUserModel>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>()  {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.imageViewUser)
        var tvName: TextView = itemView.findViewById(R.id.tvNameUser)
        var tvDescription: TextView = itemView.findViewById(R.id.tvDescriptionUser)
    }

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ListUserModel)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvName.text = listUser[position].name
        holder.tvDescription.text = listUser[position].description
        Glide.with(holder.itemView.context)
            .load(listUser[position].photoUrl)
            .circleCrop()
            .into(holder.imgAvatar)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size
}