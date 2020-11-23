package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.domain.RepoList.GetRepoListUseCase

class RepoListViewModel(private val getRepoListUseCase: GetRepoListUseCase, val dao:ArticleDao) : BaseViewModel() {
    fun fetchRepoList(): LiveData<List<Articles>> {
        return getRepoListUseCase.getRepoList()
    }
}