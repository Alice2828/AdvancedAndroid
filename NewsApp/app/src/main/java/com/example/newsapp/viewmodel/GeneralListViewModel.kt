package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.GeneralList.GetGeneralListUseCase

class GeneralListViewModel(private val getGeneralListUseCase: GetGeneralListUseCase) :
    BaseViewModel() {

    fun fetchRepoList(): LiveData<List<Articles>> {
        return getGeneralListUseCase.getRepoList()
    }
}