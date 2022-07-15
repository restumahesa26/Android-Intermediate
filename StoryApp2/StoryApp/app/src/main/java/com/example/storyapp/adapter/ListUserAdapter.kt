package com.example.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.core.util.Pair
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.model.ListUserModel
import com.example.storyapp.R
import com.example.storyapp.api.StoriesResultResponse
import com.example.storyapp.databinding.ItemUserBinding
import com.example.storyapp.ui.detail.DetailActivity

class ListUserAdapter() : PagingDataAdapter<StoriesResultResponse, ListUserAdapter.ListViewHolder>(DIFF_CALLBACK)  {

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoriesResultResponse) {
            binding.tvNameUser.text = data.name
            binding.tvDescriptionUser.text = data.description
            Glide.with(binding.root)
                .load(data.photoUrl)
                .circleCrop()
                .into(binding.imageViewUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

        val image = holder.itemView.findViewById<ImageView>(R.id.imageViewUser)
        val name = holder.itemView.findViewById<TextView>(R.id.tvNameUser)
        val description = holder.itemView.findViewById<TextView>(R.id.tvDescriptionUser)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.NAME_ID, data?.name)
            intent.putExtra(DetailActivity.DESCRIPTION_ID, data?.description)
            intent.putExtra(DetailActivity.PHOTO_URL_ID, data?.photoUrl)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(image, "profile"),
                    Pair(name, "name"),
                    Pair(description, "description"),
                )
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoriesResultResponse>() {
            override fun areItemsTheSame(oldItem: StoriesResultResponse, newItem: StoriesResultResponse): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: StoriesResultResponse, newItem: StoriesResultResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}