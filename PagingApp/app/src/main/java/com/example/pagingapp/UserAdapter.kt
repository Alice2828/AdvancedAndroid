package com.example.pagingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagingapp.R
import com.example.pagingapp.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : PagedListAdapter<User, UserAdapter.UserViewHolder>(USER_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)


        return UserViewHolder(view)


    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = getItem(position)
        user?.let { holder.bind(user) }
    }


    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageView = view.iv_avatar
        private val userName = view.tv_user_name
        private val userEmail = view.tv_user_email

        fun bind(user: User) {
            userName.text = user.firstName + " " + user.lastName
            userEmail.text = user.email

            Glide.with(imageView.context)
                .load(user.avatar)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }


    }


    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                newItem == oldItem

        }
    }


}