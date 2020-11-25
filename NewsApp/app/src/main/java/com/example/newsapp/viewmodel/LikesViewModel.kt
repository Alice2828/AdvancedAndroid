package com.example.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.database.LikesDao
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LikesViewModel(val context: Context) : BaseViewModel(), CoroutineScope {
    var dao: LikesDao? = null
    val liveData = MutableLiveData<List<Articles>>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        dao = ArticleDatabase.getDatabase(context = context).likesDao()
    }

    fun fetchRepoList() {
        launch {
            val articles = withContext(Dispatchers.IO) {
                dao?.getAll()
            }
            liveData.value = articles
        }
    }
}