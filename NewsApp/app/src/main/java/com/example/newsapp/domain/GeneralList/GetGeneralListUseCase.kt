package com.example.newsapp.domain.GeneralList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.domain.RepoList.RepoListRepository

class GetGeneralListUseCase (val generalListRepository: GeneralListRepository) {
        fun getRepoList(): LiveData<List<Articles>> {
            return generalListRepository.loadData()
        }
    }
