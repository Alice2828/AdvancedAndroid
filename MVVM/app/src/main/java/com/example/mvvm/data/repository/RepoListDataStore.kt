package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.database.PostDao
import com.example.mvvm.domain.RepoListRepository

class RepoListDataStore(apiService: ApiService, dao:PostDao) : RepoListRepository, BaseDataStore(apiService, dao) {

    override  fun loadData(): LiveData<List<ApiPost>> {
        return fetchData { service.getRepo() }
    }
}