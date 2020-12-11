package com.example.newsapp.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.data.model.Articles
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleDatabase
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.*

abstract class BaseDataStoreGeneral(
    @PublishedApi internal val service: ApiService,
    var context: Context
) {
    abstract fun loadData(): LiveData<List<Articles>>
    abstract fun loadDataSearchable(keyword: String): LiveData<List<Articles>>

    var dao: ArticleDao = ArticleDatabase.getDatabase(context).articleDao()

    inline fun fetchData(crossinline call: (ApiService) -> Deferred<Response<ApiPost>>): LiveData<List<Articles>> {
        val result = MutableLiveData<List<Articles>>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = call(service)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        result.value = response.body()?.articles
                        result.value?.let { dao.insertAll(it) }

                    } else {
                        val data = withContext(Dispatchers.IO) {
                            dao.getAll()
                        }
                        if (data.isNotEmpty()) {
                            result.value = dao.getAll()
                        }
                    }
                } catch (e: HttpException) {
                    val data = withContext(Dispatchers.IO) {
                        dao.getAll()
                    }
                    if (data.isNotEmpty()) {
                        result.value = dao.getAll()
                    }
                } catch (e: Throwable) {
                    val data = withContext(Dispatchers.IO) {
                        dao.getAll()
                    }
                    if (data.isNotEmpty()) {
                        result.value = dao.getAll()
                    }
                }
            }
        }
        return result
    }

}