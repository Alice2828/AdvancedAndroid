package com.example.newsapp.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.General
import com.example.newsapp.domain.RepoList.RepoListRepository
import retrofit2.Response

class FakeRepository : RepoListRepository {
    var arrayList = mutableListOf<Articles>()
    var observableArticles = MutableLiveData<List<Articles>>(arrayList)

    private fun refreshLiveData() {
        observableArticles.postValue(arrayList)
    }

    fun insertArticle(article: Articles) {
        arrayList.add(article)
        refreshLiveData()
    }

    override fun loadData(): LiveData<List<Articles>> {
        return observableArticles
    }

}