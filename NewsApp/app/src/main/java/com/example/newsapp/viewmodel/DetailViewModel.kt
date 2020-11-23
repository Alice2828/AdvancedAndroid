package com.example.newsapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.database.LikesDao
import com.example.newsapp.database.LikesDatabase
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DetailViewModel(private val context: Context) : ViewModel(), CoroutineScope {
    var dao: LikesDao? = null
    val liveData = MutableLiveData<Boolean>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        dao = LikesDatabase.getDatabase(context = context).likesDao()
    }

    fun haslike(itemData: Articles) {
        launch {
            val liked = withContext(Dispatchers.IO) {
                try {
                    val list = dao?.getAll()
                    var isLiked = false
                    if (list != null) {
                        for (like in list) {
                            if (like.post == itemData)
                                isLiked = true
                        }
                        isLiked
                    } else false

                } catch (e: Exception) {
                    false
                }

            }
            liveData.value = liked
        }
    }

    fun likeMovie(favourite: Boolean, itemData: Articles) {
        launch {
            if (favourite) {
                dao?.insert(Likes(itemData.id, itemData))
            } else {
                dao?.remove(itemData.id)
            }
        }
    }
}

