package com.example.simplegithubuser.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplegithubuser.databinding.ItemUserBinding
import com.example.simplegithubuser.local.FavoriteUser
import com.example.simplegithubuser.ui.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        with(Glide.with(holder.itemView)) {
            load(data.avatarUrl)
                .into(holder.imgAvatar)
        }
        holder.listUser.text = data.username

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(DetailActivity.EXTRA_DATA, data.username.toString())
            holder.itemView.context.startActivity(intentDetail)
        }
    }
    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgAvatar = binding.imageUser
        var listUser = binding.listUsername
    }
    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<FavoriteUser> =
            object : DiffUtil.ItemCallback<FavoriteUser>() {
                override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                    return oldItem == newItem
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                    return oldItem == newItem
                }
            }
    }
}