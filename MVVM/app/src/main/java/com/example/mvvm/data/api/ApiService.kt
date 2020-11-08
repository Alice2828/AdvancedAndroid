package com.example.mvvm.data.api

import com.example.mvvm.data.model.ApiPost
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top-headlines")
    fun getRepo(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Deferred<Response<ApiPost>>

}