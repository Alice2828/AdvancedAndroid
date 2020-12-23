package com.example.newsapp.fake

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Total
import com.example.newsapp.domain.CoronaList.CoronaListRepository
import com.example.newsapp.domain.CoronaList.CoronaListUseCaseInterface
import com.example.newsapp.domain.RepoList.RepoListRepository
import com.example.newsapp.domain.RepoList.RepoListUseCaseInterface

class FakeUseCaseCorona (val coronaListRepository: CoronaListRepository) : CoronaListUseCaseInterface {
    override fun getRepoList(): LiveData<Total> {
        return coronaListRepository.loadData()
    }
}