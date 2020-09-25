package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.view.adapter.viewholder.RepoListViewHolder
import com.example.mvvm.databinding.ViewRepoListItemBinding
import com.example.mvvm.viewmodel.RepoListViewModel

class RepoListAdapter (private val repoListViewModel: RepoListViewModel) :
    RecyclerView.Adapter<RepoListViewHolder>() {
    var repoList: List<ApiPost> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater, parent, false)
        return RepoListViewHolder(dataBinding, repoListViewModel)
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        holder.setup(repoList[position])
    }

    fun updateRepoList(repoList: List<ApiPost>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }
}