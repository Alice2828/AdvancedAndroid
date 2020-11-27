package com.example.newsapp.viewmodel

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.database.LikesDao
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DetailViewModel(val context: Context) : ViewModel(), CoroutineScope {
    var dao: LikesDao? = null
    var currentUser = context.getSharedPreferences("my_preferences", MODE_PRIVATE)
        .getString("emailName", "")
    val liveData = MutableLiveData<Boolean>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        dao = ArticleDatabase.getDatabase(context = context).likesDao()
    }

    fun hasLike(itemData: Articles) {
        launch {
            val liked = withContext(Dispatchers.IO) {
                try {
                    val list = dao?.getAll(currentUser)
                    var isLiked = false
                    if (list != null) {
                        for (like in list) {
                            if (like == itemData) {
                                isLiked = true
                            }
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
                val index = dao?.queryLastInsert()?.plus(1)
                dao?.insert(
                    Likes(
                        index,
                        itemData.title,
                        currentUser
                    )
                )
            } else {
                dao?.remove(itemData.title, currentUser)
            }
        }
    }
}

