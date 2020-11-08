package com.example.mvvm.domain

import androidx.lifecycle.LiveData
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.data.model.Articles

interface RepoListRepository {
    fun loadData(): LiveData<List<Articles>>
}