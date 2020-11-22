package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.domain.GeneralList.GeneralListRepository
import com.example.newsapp.domain.RepoList.RepoListRepository
import com.example.newsapp.utils.Constants

class GeneralListDataStore(apiService: ApiService, dao: ArticleDao) : GeneralListRepository,
    BaseDataStoreGeneral(apiService,  dao) {

    override fun loadData(): LiveData<List<Articles>> {
        return fetchData { service.getGeneral("bitcoin","popularity", Constants.Api_key) }
    }
    override fun loadDataSearchable(keyword:String): LiveData<List<Articles>> {
        return fetchData { service.getGeneral(keyword,"popularity", Constants.Api_key) }
    }
}