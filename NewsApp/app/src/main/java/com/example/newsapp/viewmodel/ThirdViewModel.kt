package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.DataCorona
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

class ThirdViewModel(var service: ApiServiceCorona) : BaseViewModel(), CoroutineScope {
    private val job = Job()
    val liveData = MutableLiveData<CoronaState>()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun getDataCorona() {
        launch {
            liveData.value = CoronaState.ShowLoading
            val list = withContext(Dispatchers.Main) {
                try {
                    val response = service.getConfirmedKz()
                    if (response.isSuccessful) {
                        Log.d("TAG", "")
                        val result = response.body() as Serializable
                        result
                    } else {
                        Log.d("TAG", "")
                    }
                } catch (e: Exception) {
                    Log.d("TAG", "")
                }
            }
            liveData.value = CoronaState.HideLoading
            liveData.value = CoronaState.Result(list)
        }
    }

    sealed class CoronaState {
        object ShowLoading : CoronaState()
        object HideLoading : CoronaState()
        data class Result(val list: Serializable?) : CoronaState()
    }
}
