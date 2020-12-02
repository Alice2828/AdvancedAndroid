package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.viewholder.RepoListViewHolder
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewRepoListItemBinding
import com.example.newsapp.view.activities.MainActivity

class RepoListAdapter(val activity: MainActivity) :
    RecyclerView.Adapter<RepoListViewHolder>() {
    var repoList: List<Articles> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater, parent, false)
        val context = parent.context
        return RepoListViewHolder(activity, dataBinding, context)
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        holder.setup(repoList[position])

    }

    fun updateRepoList(repoList: List<Articles>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }
}
