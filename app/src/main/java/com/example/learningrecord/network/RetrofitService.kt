package com.example.learningrecord.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitService {
    lateinit var okHttpClient:OkHttpClient

    private const val BASE_URL ="https://www.wanandroid.com/"

    fun <T> createService(mClass:Class<T>):T{
        val builder:OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClient=builder.build()
        val retrofit:Retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(mClass) as T
    }


}