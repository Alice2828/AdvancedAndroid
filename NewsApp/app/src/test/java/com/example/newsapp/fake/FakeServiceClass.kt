package com.example.newsapp.fake

import com.example.newsapp.data.api.ApiServiceCorona
import com.example.newsapp.data.model.General
import retrofit2.Response

class FakeServiceClass:ApiServiceCorona {
    override suspend fun getTotal(): Response<General> {
        TODO("Not yet implemented")
    }
}