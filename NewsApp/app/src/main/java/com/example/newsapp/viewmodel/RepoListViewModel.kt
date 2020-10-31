package com.example.newsapp.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.GeneralList.GetGeneralListUseCase
import com.example.newsapp.domain.RepoList.GetRepoListUseCase

class RepoListViewModel(private val getRepoListUseCase: GetRepoListUseCase) : BaseViewModel() {
    val isLoading = ObservableBoolean()

    fun fetchRepoList(): LiveData<List<Articles>> {
//        isLoading = true
        return getRepoListUseCase.getRepoList()
//        { isSuccess, response ->
//            isLoading = false
//            if (isSuccess) {
//                repoListLive.value = response?.articles
//            }
//
//        }
    }

//
//    fun onRefresh() {
//        isLoading.set(true)
//        try {
//            fetchRepoList()
//        } catch (e: Exception) {
//
//        } finally {
//            isLoading.set(false)
//        }
//    }
}