package com.example.mvvm.viewmodel


import androidx.lifecycle.LiveData
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.domain.GetRepoListUseCase

class RepoListViewModel(val getRepoListUseCase: GetRepoListUseCase) : BaseViewModel() {
    fun fetchRepoList(): LiveData<List<ApiPost>> {
        return getRepoListUseCase.getRepoList()
    }
}