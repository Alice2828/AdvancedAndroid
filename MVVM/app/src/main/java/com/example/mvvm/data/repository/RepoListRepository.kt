package com.example.mvvm.data.repository

import com.example.mvvm.data.api.ApiClient
import com.example.mvvm.data.model.GitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListRepository {
    // GET repo list
    fun getRepoList(onResult: (isSuccess: Boolean, response: GitResponse?) -> Unit) {

        ApiClient.instance.getRepo().enqueue(object : Callback<GitResponse> {
            override fun onResponse(call: Call<GitResponse>?, response: Response<GitResponse>?) {
                if (response != null && response.isSuccessful)
                    onResult(true, response.body()!!)
                else
                    onResult(false, null)
            }

            override fun onFailure(call: Call<GitResponse>?, t: Throwable?) {
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