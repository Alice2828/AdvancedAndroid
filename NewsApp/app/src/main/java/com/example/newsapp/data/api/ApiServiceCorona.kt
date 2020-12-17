package com.example.newsapp.data.api

import com.example.newsapp.data.model.DataCorona
import com.example.newsapp.data.model.General
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceCorona {
    @GET("summary")
    suspend fun getTotal(
    ): Response<General>

    @GET("dayone/country/kazakhstan/status/confirmed")
    suspend fun getConfirmedKz(
    ): Response<List<DataCorona>>

    @GET("dayone/country/kazakhstan")
    suspend fun getKz(
    ): Response<List<DataCorona>>
}