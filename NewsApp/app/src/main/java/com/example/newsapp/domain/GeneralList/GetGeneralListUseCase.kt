package com.example.newsapp.domain.GeneralList

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.Articles

class GetGeneralListUseCase(val generalListRepository: GeneralListRepository) {
    fun getRepoList(keyword: String): LiveData<List<Articles>> {
        if (keyword == "default")
            return generalListRepository.loadData()
        return generalListRepository.loadDataSearchable(keyword)
    }
}
