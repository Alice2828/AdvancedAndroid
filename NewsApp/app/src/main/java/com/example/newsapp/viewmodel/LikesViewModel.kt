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
    private var preferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private var name = preferences.getString("emailName", "")!!

    init {
        dao = ArticleDatabase.getDatabase(context = context, name).likesDao()
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