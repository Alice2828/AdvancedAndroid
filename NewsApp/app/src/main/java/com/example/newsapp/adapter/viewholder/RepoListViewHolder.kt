package com.example.newsapp.adapter.viewholder

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.BR
import com.example.newsapp.R
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewRepoListItemBinding
import com.example.newsapp.view.DetailFragment
import com.example.newsapp.viewmodel.RepoListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_repo_list_item.view.*


class RepoListViewHolder constructor(
    val dataBinding: ViewRepoListItemBinding,
    private val repoListViewModel: RepoListViewModel,
    val context: Context,
    val activity: FragmentActivity?
) : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.item_avatar

    fun setup(itemData: Articles) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Picasso.get().load(itemData.urlToImage).into(avatarImage)

        itemView.setOnClickListener {
           // val context = itemView.context
            val fragment = DetailFragment(itemData)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_nav_fragment, fragment)
                ?.commit()
        }
    }
}