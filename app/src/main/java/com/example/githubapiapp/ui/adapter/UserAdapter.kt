package com.example.githubapiapp.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapiapp.data.response.ItemsItem
import com.example.githubapiapp.databinding.ItemUserBinding
import com.example.githubapiapp.ui.DetailUserActivity


class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class MyViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.tvItemName.text = user.login

            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(binding.ivAvatar)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_LOGIN,user.login)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
