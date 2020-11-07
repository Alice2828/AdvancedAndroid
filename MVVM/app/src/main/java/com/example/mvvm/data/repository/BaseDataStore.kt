package com.example.mvvm.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.api.ApiService
import com.example.mvvm.data.model.ApiPost
import com.example.mvvm.database.PostDao
//import com.example.mvvm.database.PostDao
//import com.example.mvvm.database.PostDatabase
import retrofit2.HttpException
import retrofit2.Response
import kotlinx.coroutines.*
import timber.log.Timber

abstract class BaseDataStore(@PublishedApi internal val service: ApiService, var dao: PostDao) {

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
                        result.value?.let { dao.insertAll(it) }
                    } else {
                        val data = withContext(Dispatchers.IO) {
                            dao.getAll()
                        }
                        if (data.isNotEmpty()) {
                            result.value = dao.getAll()
                            Timber.d("Error occurred with code ${response.code()}")
                        }
                    }
                } catch (e: HttpException) {
                    val data = withContext(Dispatchers.IO) {
                        dao.getAll()
                    }
                    if (data.isNotEmpty()) {
                        result.value = dao.getAll()
                    }
                    Timber.d("Error: ${e.message()}")
                } catch (e: Throwable) {
                    val data = withContext(Dispatchers.IO) {
                        dao.getAll()
                    }
                    if (data.isNotEmpty()) {
                        result.value = dao.getAll()
                    }
                    Timber.d("Error: ${e.message}")
                }
            }
        }
        return result
    }

//    sealed class AppResult<out T> {
//
//        data class Success<out T>(val successData : T) : AppResult<T>()
//        class Error(val exception: java.lang.Exception, val message: String = exception.localizedMessage)
//            : AppResult<Nothing>()
//    }
//
//    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
//        val error = ApiErrorUtils.parseError(resp)
//        return AppResult.Error(Exception(error.message))
//    }
//
//    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
//        response.body()?.let {
//            return AppResult.Success(it)
//        } ?: return handleApiError(response)
//    }
}