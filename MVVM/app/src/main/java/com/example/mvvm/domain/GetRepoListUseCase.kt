package com.example.mvvm.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.domain.RepoListRepository

class GetRepoListUseCase (val repoListRepository: RepoListRepository){
    val repoListLive = MutableLiveData<List<ApiPost>>()
    fun getRepoList(): LiveData<List<ApiPost>> {
        return repoListRepository.loadData()
    }
}