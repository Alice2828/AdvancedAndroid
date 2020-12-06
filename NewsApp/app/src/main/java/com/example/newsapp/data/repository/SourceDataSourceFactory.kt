package com.example.newsapp.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles

class SourceDataSourceFactory(val service: ApiService, var context: Context): DataSource.Factory<Int, Articles>(){

    val userLiveDataSource = MutableLiveData<SourceDataStore>()

    override fun create(): DataSource<Int, Articles> {
        val userDataSource =
            SourceDataStore(service, context)
        userLiveDataSource.postValue(userDataSource)

        return userDataSource

    }
}