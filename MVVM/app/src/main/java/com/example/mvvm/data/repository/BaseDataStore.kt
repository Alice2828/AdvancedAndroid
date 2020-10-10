package com.example.mvvm.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.ApiPost
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseDataStore(@PublishedApi internal val service: ApiService) {

    abstract fun loadData(): LiveData<List<ApiPost>>


    inline fun fetchData(crossinline call: (ApiService) -> Deferred<Response<List<ApiPost>>>): LiveData<List<ApiPost>> {
        val result = MutableLiveData<List<ApiPost>>()
        CoroutineScope(Dispatchers.IO).launch {
            val request = call(service)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    if (response.isSuccessful) {
                        result.value = response.body()
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