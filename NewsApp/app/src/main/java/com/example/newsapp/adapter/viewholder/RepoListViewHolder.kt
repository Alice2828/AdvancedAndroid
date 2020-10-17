package com.example.newsapp.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.BR
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewRepoListItemBinding
import com.example.newsapp.view.DetailActivity
import com.example.newsapp.viewmodel.RepoListViewModel
//import com.squareup.picasso.Picasso
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
        Glide.with(context)
            .load(itemData.urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(avatarImage)


        itemView.setOnClickListener {
            //     val fragment = DetailFragment(itemData)
//            activity?.supportFragmentManager?.beginTransaction()
//                ?.replace(R.id.main_nav_fragment, fragment)
//                ?.addToBackStack(null)
//                ?.commit()
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("itemData", itemData)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}