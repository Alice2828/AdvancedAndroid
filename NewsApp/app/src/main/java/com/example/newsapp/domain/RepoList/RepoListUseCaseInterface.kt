package com.example.newsapp.domain.RepoList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles

interface RepoListUseCaseInterface {
    fun getRepoList(): LiveData<List<Articles>>
}