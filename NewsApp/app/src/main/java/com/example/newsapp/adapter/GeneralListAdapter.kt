package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.adapter.viewholder.GeneralListViewHolder
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.ViewGeneralListItemBinding

class GeneralListAdapter :
    RecyclerView.Adapter<GeneralListViewHolder>() {
    var repoList: List<Articles> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewGeneralListItemBinding.inflate(inflater, parent, false)
        val context = parent.context
        return GeneralListViewHolder(dataBinding, context)
    }

    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: GeneralListViewHolder, position: Int) {
        holder.setup(repoList[position])

    }

    fun updateRepoList(repoList: List<Articles>) {
        this.repoList = repoList
        notifyDataSetChanged()
    }
}
