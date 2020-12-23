package com.example.newsapp.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.model.Total
import com.example.newsapp.domain.CoronaList.CoronaListRepository

class FakeRepositoryCorona : CoronaListRepository {
    var observableTotal = MutableLiveData(Total())

    fun insertArticle(total: Total) {
        observableTotal.postValue(total)
    }

    override fun loadData(): LiveData<Total> {
        return observableTotal
    }

}