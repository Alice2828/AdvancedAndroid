package com.example.mvvm.data.api

import com.example.mvvm.data.model.ApiPost
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("photos")
    fun getRepo(): Deferred<Response<List<ApiPost>>>
}