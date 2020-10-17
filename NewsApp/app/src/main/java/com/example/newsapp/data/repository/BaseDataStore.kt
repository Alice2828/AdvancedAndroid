package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.data.model.Articles
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseDataStore(@PublishedApi internal val service: ApiService) {
    abstract fun loadData(): LiveData<List<Articles>>


    inline fun fetchData(crossinline call: (ApiService) -> Deferred<Response<ApiPost>>): LiveData<List<Articles>> {
        val result = MutableLiveData<List<Articles>>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = call(service)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        result.value = response.body()?.articles
                    } else {

                        Timber.d("Error occurred with code ${response.code()}")
                    }
                } catch (e: HttpException) {
                    Timber.d("Error: ${e.message()}")
                } catch (e: Throwable) {
                    Timber.d("Error: ${e.message}")
                }
            }
        }
        return result
    }

}