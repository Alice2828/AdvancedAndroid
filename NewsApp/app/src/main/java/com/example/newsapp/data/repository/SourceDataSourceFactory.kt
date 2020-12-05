package com.example.newsapp.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles

class SourceDataSourceFactory(val service: ApiService): DataSource.Factory<Int, Articles>(){

    val userLiveDataSource = MutableLiveData<SourceDataStore>()

    override fun create(): DataSource<Int, Articles> {
        val userDataSource =
            SourceDataStore(service)
        userLiveDataSource.postValue(userDataSource)

        return userDataSource

    }
}