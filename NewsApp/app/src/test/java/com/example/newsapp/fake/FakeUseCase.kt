package com.example.newsapp.fake

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.RepoList.GetRepoListUseCase
import com.example.newsapp.domain.RepoList.RepoListRepository
import com.example.newsapp.domain.RepoList.RepoListUseCaseInterface

class FakeUseCase(val repoListRepository: RepoListRepository) :RepoListUseCaseInterface {
   override fun getRepoList(): LiveData<List<Articles>> {
        return repoListRepository.loadData()
    }
}