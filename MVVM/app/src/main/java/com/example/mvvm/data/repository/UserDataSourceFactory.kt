package com.example.mvvm.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.Articles
import com.example.mvvm.database.ArticleDao


class UserDataSourceFactory(val service: ApiService, val dao: ArticleDao): DataSource.Factory<Int, Articles>(){

    val userLiveDataSource = MutableLiveData<BaseDataStore>()

    override fun create(): DataSource<Int, Articles> {
        val userDataSource =
            BaseDataStore(service, dao)
        userLiveDataSource.postValue(userDataSource)

        return userDataSource

    }
}