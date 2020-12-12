package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.domain.RepoList.GetRepoListUseCase
import com.example.newsapp.domain.RepoList.RepoListUseCaseInterface

class RepoListViewModel(private val getRepoListUseCase: RepoListUseCaseInterface) : BaseViewModel() {
    fun fetchRepoList(): LiveData<List<Articles>> {
        return getRepoListUseCase.getRepoList()
    }
}