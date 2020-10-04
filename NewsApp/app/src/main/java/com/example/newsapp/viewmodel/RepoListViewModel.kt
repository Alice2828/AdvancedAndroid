package com.example.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.repository.RepoListRepository

class RepoListViewModel(val repoListRepository: RepoListRepository) : BaseViewModel() {
    val repoListLive = MutableLiveData<List<Articles>>()

    fun fetchRepoList() {
        dataLoading.value = true
        repoListRepository.getRepoList { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                repoListLive.value = response?.articles
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}