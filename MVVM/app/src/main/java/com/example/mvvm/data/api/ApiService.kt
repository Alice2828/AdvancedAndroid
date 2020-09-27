package com.example.mvvm.data.api

import com.example.mvvm.data.model.ApiPost
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {


    @GET("photos")
    fun getRepo(): Call<List<ApiPost>>
}