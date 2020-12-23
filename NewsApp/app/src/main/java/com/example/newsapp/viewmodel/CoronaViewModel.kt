package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Total
import com.example.newsapp.domain.CoronaList.CoronaListUseCaseInterface


class CoronaViewModel(private val getCoronaListUseCase: CoronaListUseCaseInterface) :
    BaseViewModel() {
    fun getTotal(): LiveData<Total> {
        return getCoronaListUseCase.getRepoList()
    }
}