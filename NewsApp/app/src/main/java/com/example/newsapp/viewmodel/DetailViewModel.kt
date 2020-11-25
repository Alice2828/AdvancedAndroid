package com.example.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.database.LikesDao
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class DetailViewModel(context: Context) : ViewModel(), CoroutineScope {
    var dao: LikesDao? = null
    val liveData = MutableLiveData<Boolean>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var preferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    private var name = preferences.getString("emailName", "")!!
    init {
        dao = ArticleDatabase.getDatabase(context = context, name).likesDao()
    }

    fun haslike(itemData: Articles) {
        launch {
            val liked = withContext(Dispatchers.IO) {
                try {
                    val list = dao?.getAll()
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
                val index= dao?.queryLastInsert()?.plus(1)
                dao?.insert(Likes(index, itemData.title))
               // dao?.update( itemData.title)
//                if (index != null) {
//                   // dao?.insert(Likes(index))
//                    dao?.update(index, itemData.id)
//                }
//                else
//                {
//                  //  dao?.insert(Likes(0))
//                    dao?.update(0, itemData.id)
//                }
            } else {
                dao?.remove(itemData.title)
            }
        }
    }
}

