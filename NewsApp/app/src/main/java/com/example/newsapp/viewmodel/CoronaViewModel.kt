package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.Total
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

class CoronaViewModel(var service: ApiServiceCorona) : BaseViewModel(), CoroutineScope {
    val liveData = MutableLiveData<State>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    fun getTotal() {
        launch {
            liveData.value = State.ShowLoading
            val result = withContext(Dispatchers.Main) {
                try {
                    val response = service.getTotal()
                    if (response.isSuccessful) {
                        val resulting = response.body()?.Global
                        resulting
                    } else {
                        Log.e("TAG", "error")
                    }

                } catch (exception: Exception) {
                    Log.e("TAG", "error2")
                }
            }
            liveData.value = State.HideLoading
            liveData.value = State.Result(result)

        }
    }

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        data class Result(val result: Serializable?) : State()
    }
}