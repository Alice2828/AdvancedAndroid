package com.example.newsapp.domain.CoronaList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Total

class CoronaListUseCase(val coronaListRepository: CoronaListRepository) : CoronaListUseCaseInterface {
    override fun getRepoList(): LiveData<Total> {
        return coronaListRepository.loadData()
    }
}