package com.example.pagingapp.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceBuilder {

    //Base USL
    private val BASE_URL = "https://reqres.in/api/"

    //create logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create okHttp client
    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(logger)

    //create Retrofit builder
    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //create retrofit instance
    private val retrofit = builder.build()

    fun<T> buildService(serviceType : Class<T>):T{
        return retrofit.create(serviceType)
    }
}