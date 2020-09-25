package com.example.mvvm.data.repository

import com.example.mvvm.data.api.ApiClient
import com.example.mvvm.data.model.ApiPost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListRepository {
    // GET repo list
    fun getRepoList(onResult: (isSuccess: Boolean, response: List<ApiPost>?) -> Unit) {

        ApiClient.instance.getRepo().enqueue(object : Callback<List<ApiPost>> {
            override fun onResponse(call: Call<List<ApiPost>>?, response: Response<List<ApiPost>>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body())
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<List<ApiPost>>?, t: Throwable?) {
                onResult(false, null)
            }

        })
    }

    companion object {
        private var INSTANCE: RepoListRepository? = null
        fun getInstance() = INSTANCE
            ?: RepoListRepository().also {
                INSTANCE = it
            }
    }
}