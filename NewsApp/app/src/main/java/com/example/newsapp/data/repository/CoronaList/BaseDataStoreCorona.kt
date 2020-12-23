package com.example.newsapp.data.repository.CoronaList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.General
import com.example.newsapp.data.model.Total
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleDatabase
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response

abstract class BaseDataStoreCorona(
    @PublishedApi internal val service: ApiServiceCorona,
    var context: Context
) {
    abstract fun loadData(): LiveData<Total>

    inline fun fetchData(crossinline call: (ApiServiceCorona) -> Deferred<Response<General>>): LiveData<Total> {
        val result = MutableLiveData<Total>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = call(service)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        result.value = response.body()?.Global
                    }
                } catch (e: HttpException) {

                } catch (e: Throwable) {

                }
            }
        }
        return result
    }

}