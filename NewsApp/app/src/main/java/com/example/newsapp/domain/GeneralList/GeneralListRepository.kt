package com.example.newsapp.domain.GeneralList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles

interface GeneralListRepository {
    fun loadData(): LiveData<List<Articles>>
    fun loadDataSearchable(keyword:String): LiveData<List<Articles>>
}