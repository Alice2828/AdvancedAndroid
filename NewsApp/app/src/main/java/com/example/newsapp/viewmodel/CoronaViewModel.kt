package com.example.newsapp.viewmodel

import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.Total
import kotlinx.coroutines.*

class CoronaViewModel(var service: ApiServiceCorona) : BaseViewModel() {

    fun getTotal(): Total {

        var result = Total()

        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getTotal()
            try {
                withContext(Dispatchers.Main) {
                    val response = request.await()
                    when {
                        response.isSuccessful -> {
                            result = response.body()?.Global as Total
                        }
                    }
                }
            } catch (exception: Exception) {

            }
        }
        return result
    }
}