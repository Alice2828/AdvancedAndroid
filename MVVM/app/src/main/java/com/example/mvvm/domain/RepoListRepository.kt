package com.example.mvvm.domain

import androidx.lifecycle.LiveData
import com.example.mvvm.data.model.ApiPost

interface RepoListRepository {
    fun loadData(): LiveData<List<ApiPost>>
}