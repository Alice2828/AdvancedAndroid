package com.example.newsapp.data.api

import com.example.newsapp.data.model.ApiPost
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("top-headlines")
    fun getRepo(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Call<ApiPost>
}