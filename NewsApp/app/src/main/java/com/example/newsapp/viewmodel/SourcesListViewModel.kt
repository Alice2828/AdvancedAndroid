package com.example.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.repository.SourceData.SourceDataStore
import com.example.newsapp.data.repository.SourceData.SourceDataSourceFactory


class SourcesListViewModel(service: ApiService, context: Context) : BaseViewModel() {

    var postsLiveData: LiveData<PagedList<Articles>>
    val liveDataSource: LiveData<SourceDataStore>

    init {
        val itemDataSourceFactory = SourceDataSourceFactory(service, context)

        liveDataSource = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(SourceDataStore.PAGE_SIZE)
            .build()

        postsLiveData = LivePagedListBuilder(itemDataSourceFactory, config)
            .build()

    }
}