package com.example.mvvm.view.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import com.example.mvvm.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.adapter.ClickHandler
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.databinding.ViewRepoListItemBinding
import com.example.mvvm.view.DetailActivity
import com.example.mvvm.viewmodel.RepoListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_repo_list_item.view.*

class RepoListViewHolder constructor(
    val dataBinding: ViewRepoListItemBinding,
    private val repoListViewModel: RepoListViewModel,
    val context: Context
) : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.item_avatar

    fun setup(itemData: ApiPost) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Picasso.get().load(itemData.thumbnailUrl).into(avatarImage)
        itemView.setOnClickListener() {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}