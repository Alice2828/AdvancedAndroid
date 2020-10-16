package com.example.newsapp.viewmodel

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.GetRepoListUseCase

class RepoListViewModel(val getRepoListUseCase: GetRepoListUseCase) : BaseViewModel() {
    var mIsLoading = ObservableBoolean(false)
    var mOnRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        mIsLoading.set(true)
        fetchRepoList()
        mIsLoading.set(false)
    }

    fun fetchRepoList(): LiveData<List<Articles>> {
        return getRepoListUseCase.getRepoList()
    }

    fun getOnRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return mOnRefreshListener
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }
}

@BindingAdapter("bind:refreshState", "bind:onRefresh")
fun configureSwipeRefreshLayout(
    layout: SwipeRefreshLayout, isLoading: Boolean,
    listener: SwipeRefreshLayout.OnRefreshListener
) {
    layout.setOnRefreshListener(listener)
    layout.post { layout.isRefreshing = isLoading }

}