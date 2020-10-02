package com.example.mvvm.view.adapter.viewholder

import android.R.attr.fragment
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.BR
import com.example.mvvm.R
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.databinding.ViewRepoListItemBinding
import com.example.mvvm.view.DetailFragment
import com.example.mvvm.viewmodel.RepoListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_repo_list_item.view.*


class RepoListViewHolder constructor(
    val dataBinding: ViewRepoListItemBinding,
    private val repoListViewModel: RepoListViewModel,
    val context: Context,
    val activity: FragmentActivity?
) : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.item_avatar

    fun setup(itemData: ApiPost) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Picasso.get().load(itemData.thumbnailUrl).into(avatarImage)

        itemView.setOnClickListener {
            val context = itemView.context
            val fragment = DetailFragment(itemData)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_nav_fragment, fragment)
                ?.commit()
        }
    }
}