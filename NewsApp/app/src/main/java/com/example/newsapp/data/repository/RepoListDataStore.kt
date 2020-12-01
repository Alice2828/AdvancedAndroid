package com.example.newsapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.RepoList.RepoListRepository
import com.example.newsapp.utils.Constants.Companion.Api_key

class RepoListDataStore(apiService: ApiService, context: Context) : RepoListRepository, BaseDataStore(apiService, context) {
    override fun loadData(): LiveData<List<Articles>> {
        return fetchData {
            service.getRepo("us", Api_key)
        }
    }
}