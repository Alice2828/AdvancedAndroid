package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.RepoListRepository
import com.example.newsapp.utils.Constants.Companion.Api_key

class RepoListDataStore(apiService: ApiService) : RepoListRepository, BaseDataStore(apiService) {

    override fun loadData(): LiveData<List<Articles>> {
        return fetchData { service.getRepo("us", Api_key) }
    }
}