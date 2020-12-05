package com.example.newsapp.data.api

import com.example.newsapp.data.model.ApiPost
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top-headlines")
    fun getRepo(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Deferred<Response<ApiPost>>

    @GET("everything")
    fun getGeneral(
        @Query("qInTitle") q: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Deferred<Response<ApiPost>>

    @GET("everything")
    fun getSources(
        @Query("qInTitle") q: String,
        //@Query("pageSize") pagSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Deferred<Response<ApiPost>>
}