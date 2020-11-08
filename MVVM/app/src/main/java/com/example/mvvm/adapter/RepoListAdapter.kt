package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.data.model.Articles
import com.example.mvvm.database.ArticleDatabase
import com.example.mvvm.view.adapter.viewholder.RepoListViewHolder
import com.example.mvvm.databinding.ViewRepoListItemBinding
import com.example.mvvm.viewmodel.RepoListViewModel

class RepoListAdapter(
    private val repoListViewModel: RepoListViewModel,
    private val activity: FragmentActivity?
) :
    PagedListAdapter<Articles, RepoListViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater, parent, false)
        return RepoListViewHolder(dataBinding, activity)
    }

  //  override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        getItem(position)?.let { holder.setup(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Articles>() {
            override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean =
                oldItem == newItem
        }
    }
}
