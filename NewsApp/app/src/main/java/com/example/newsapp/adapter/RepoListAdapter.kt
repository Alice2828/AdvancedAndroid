package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.viewholder.RepoListViewHolder
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.databinding.ViewRepoListItemBinding
import com.example.newsapp.viewmodel.RepoListViewModel

class RepoListAdapter(
    private val repoListViewModel: RepoListViewModel,
    private val activity: FragmentActivity?
) :
    RecyclerView.Adapter<RepoListViewHolder>() {
    var repoList: List<ApiPost> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater, parent, false)
        val context = parent.context
        return RepoListViewHolder(dataBinding, repoListViewModel, context, activity)
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
