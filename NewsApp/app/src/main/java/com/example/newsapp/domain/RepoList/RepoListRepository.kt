package com.example.newsapp.domain.RepoList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles

interface RepoListRepository {
    fun loadData(): LiveData<List<Articles>>
}