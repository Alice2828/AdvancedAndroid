package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiClient
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.utils.Constants.Companion.Api_key
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RepoListRepository(val apiServece: ApiService) {
    // GET repo list
    fun getRepoList(onResult: (isSuccess: Boolean, response: ApiPost?) -> Unit) {
        val country = "us"
        apiServece.getRepo(country, Api_key).enqueue(object : Callback<ApiPost> {
            override fun onResponse(
                call: Call<ApiPost>?,
                response: Response<ApiPost>?
            ) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body())
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<ApiPost>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

}