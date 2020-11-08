package com.example.mvvm.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.data.model.Articles
import com.example.mvvm.domain.RepoListRepository

class GetRepoListUseCase (val repoListRepository: RepoListRepository){
    fun getRepoList(): LiveData<List<Articles>> {
        return repoListRepository.loadData()
    }
}