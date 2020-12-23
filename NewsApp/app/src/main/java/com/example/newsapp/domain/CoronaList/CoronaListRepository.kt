package com.example.newsapp.domain.CoronaList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Total

interface CoronaListRepository {
    fun loadData(): LiveData<Total>
}