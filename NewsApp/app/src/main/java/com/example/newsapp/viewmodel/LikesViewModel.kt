package com.example.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.LikesDao
import com.example.newsapp.database.LikesDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LikesViewModel(val context: Context) : BaseViewModel(), CoroutineScope {
    var dao: LikesDao? = null
    val liveData = MutableLiveData<List<Articles>>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        dao = LikesDatabase.getDatabase(context = context).likesDao()
    }


    fun fetchRepoList() {
        launch {

            var articles = withContext(Dispatchers.IO) {
                var likes = dao?.getAll()
                if (likes != null) {
                    var listArticles = ArrayList<Articles>()
                    for (like in likes) {
                        listArticles.add(like.post)
                    }
                    listArticles
                } else ArrayList<Articles>()
            }
            liveData.value = articles
        }
    }
}