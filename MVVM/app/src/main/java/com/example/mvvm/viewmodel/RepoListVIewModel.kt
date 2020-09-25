package com.example.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.repository.RepoListRepository
import com.example.mvvm.data.model.Item

class RepoListViewModel : BaseViewModel() {
    val repoListLive = MutableLiveData<List<Item>>()

    fun fetchRepoList() {
        dataLoading.value = true
        RepoListRepository.getInstance().getRepoList { isSuccess, response ->
            dataLoading.value = false
            if (isSuccess) {
                repoListLive.value = response?.items
                empty.value = false
            } else {
                empty.value = true
            }
        }
    }
}