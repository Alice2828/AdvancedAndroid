package com.example.newsapp.adapter.viewholder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapp.BR
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewSourceItemBinding
import com.example.newsapp.view.activities.DetailActivity
import com.example.newsapp.view.activities.SourcesActivity
import kotlinx.android.synthetic.main.fragment_sources_list.*
import kotlinx.android.synthetic.main.view_source_item.view.*


class SourceListViewHolder constructor(
    val activity: SourcesActivity,
    val dataBinding: ViewSourceItemBinding,
    val context: Context,
) : RecyclerView.ViewHolder(dataBinding.root) {

    val avatarImage = itemView.imagePost

    fun setup(itemData: Articles) {
        dataBinding.setVariable(BR.itemData, itemData)
        dataBinding.executePendingBindings()
        Glide.with(context)
            .load(itemData.urlToImage)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(avatarImage)


        itemView.setOnClickListener {
            val context = itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("itemData", itemData)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}