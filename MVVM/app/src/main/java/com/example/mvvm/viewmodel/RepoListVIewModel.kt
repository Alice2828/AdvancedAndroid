package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.Articles
import com.example.mvvm.data.repository.BaseDataStore
import com.example.mvvm.data.repository.UserDataSourceFactory
import com.example.mvvm.database.ArticleDao

class RepoListViewModel(service: ApiService, dao: ArticleDao) : BaseViewModel() {

    var postsLiveData: LiveData<PagedList<Articles>>
    val liveDataSource:LiveData<BaseDataStore>

    init {
        val itemDataSourceFactory = UserDataSourceFactory(service, dao)

        liveDataSource = itemDataSourceFactory.userLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(BaseDataStore.PAGE_SIZE)
            .build()

        postsLiveData = LivePagedListBuilder(itemDataSourceFactory,config)
            .build()

    }
}