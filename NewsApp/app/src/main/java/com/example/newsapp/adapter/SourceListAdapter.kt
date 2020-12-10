package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.adapter.viewholder.SourceListViewHolder
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewSourceItemBinding
import com.example.newsapp.view.activities.SourcesActivity

class SourceListAdapter(val activity: SourcesActivity) :
    PagedListAdapter<Articles, SourceListViewHolder>(diffCallback){
    var repoList: List<Articles> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewSourceItemBinding.inflate(inflater, parent, false)
        val context = parent.context
        return SourceListViewHolder(activity, dataBinding, context)
    }

    override fun onBindViewHolder(holder: SourceListViewHolder, position: Int) {
        getItem(position)?.let { holder.setup(it) }
    }
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean =
                oldItem == newItem
        }
    }
}