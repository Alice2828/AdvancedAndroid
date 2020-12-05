package com.example.newsapp.data.repository

import androidx.paging.PageKeyedDataSource
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.Articles
import com.example.newsapp.utils.Constants.Companion.Api_key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SourceDataStore(@PublishedApi internal val service: ApiService) :
    PageKeyedDataSource<Int, Articles>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getSources("coronavirus", FIRST_PAGE, Api_key)
            try {
                withContext(Dispatchers.Main) {
                    val response = request.await()
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()?.articles
                            if (listing != null) {
                                callback.onResult(listing, null, FIRST_PAGE + 1)
                            }
                        }
                    }
                }

            } catch (exception: Exception) {
                Timber.e("Failed to fetch data!")
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getSources("coronavirus", FIRST_PAGE, Api_key)
            try {
                withContext(Dispatchers.Main) {
                    val response = request.await()
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()?.articles
                            val key = params.key + 1
                            if (listing != null) {
                                callback.onResult(listing, key)
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                Timber.e("Failed to fetch data!")
            }

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {

        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getSources("coronavirus", FIRST_PAGE, Api_key)
            try {
                withContext(Dispatchers.Main) {
                    val response = request.await()
                    when {
                        response.isSuccessful -> {
                            val listing = response.body()?.articles
                            val key = if (params.key > 1) params.key - 1 else 0
                            if (listing != null) {
                                callback.onResult(listing, key)
                            }
                        }
                    }
                }

            } catch (exception: Exception) {
                Timber.e("Failed to fetch data!")
            }


        }
    }

    companion object {
        const val PAGE_SIZE = 15
        const val FIRST_PAGE = 1
    }
}