package com.example.newsapp.data.api

import com.example.newsapp.data.model.General
import com.example.newsapp.data.model.Total
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceCorona {
    @GET("summary")
    fun getTotal(
    ): Deferred<Response<General>>
}