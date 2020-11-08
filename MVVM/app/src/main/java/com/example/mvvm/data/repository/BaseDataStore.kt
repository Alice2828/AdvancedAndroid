package com.example.mvvm.data.repository

import androidx.paging.PageKeyedDataSource
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.data.model.Articles
import com.example.mvvm.database.ArticleDao
import com.example.mvvm.utils.Constants.Companion.Api_key
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import timber.log.Timber

class BaseDataStore(@PublishedApi internal val service: ApiService, var dao: ArticleDao) :
    PageKeyedDataSource<Int, Articles>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Articles>
    ) {
//        val call = service.getRepo("us", FIRST_PAGE, Api_key)
//
//        call.enqueue(object : Callback<ApiPost> {
//            override fun onFailure(call: Call<ApiPost>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<ApiPost>, response: Response<ApiPost>) {
//                if(response.isSuccessful){
//                    val apiResponse = response.body()!!
//                    val responseItems = apiResponse.articles
//
//                    responseItems.let {
//                        callback.onResult(responseItems,null, FIRST_PAGE+1)
//                    }
//                }
//
//            }
//
//        })

        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getRepo("us", FIRST_PAGE, Api_key)

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

//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
//        val call = service.getRepo("us", params.key, Api_key)
//
//        call.enqueue(object : Callback<ApiPost> {
//            override fun onFailure(call: Call<ApiPost>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<ApiPost>, response: Response<ApiPost>) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body()!!
//                    val responseItems = apiResponse.articles
//
//                    val key = params.key + 1
//
//                    responseItems.let {
//                        callback.onResult(responseItems, key)
//                    }
//                }
//
//            }
//
//        })
//
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
//        val call = service.getRepo("us", params.key, Api_key)
//
//
//        call.enqueue(object : Callback<ApiPost> {
//            override fun onFailure(call: Call<ApiPost>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<ApiPost>, response: Response<ApiPost>) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body()!!
//                    val responseItems = apiResponse.articles
//
//                    val key = if (params.key > 1) params.key - 1 else 0
//
//                    responseItems.let {
//                        callback.onResult(responseItems, key)
//                    }
//                }
//
//            }
//
//        })
//
//    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Articles>) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getRepo("us", FIRST_PAGE, Api_key)
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
            val request = service.getRepo("us", FIRST_PAGE, Api_key)
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
        const val PAGE_SIZE = 6
        const val FIRST_PAGE = 1

    }

}