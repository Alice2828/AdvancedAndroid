package com.example.newsapp.domain.RepoList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles

class GetRepoListUseCase(val repoListRepository: RepoListRepository) : RepoListUseCaseInterface {
    override fun getRepoList(): LiveData<List<Articles>> {
        return repoListRepository.loadData()
    }
}