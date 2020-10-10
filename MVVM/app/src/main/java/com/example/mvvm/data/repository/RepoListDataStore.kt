package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.domain.RepoListRepository

class RepoListDataStore(apiService: ApiService) : RepoListRepository, BaseDataStore(apiService) {

    override fun loadData(): LiveData<List<ApiPost>> {
        return fetchData { service.getRepo() }
    }
}