package com.example.learningrecord.network


import com.example.learningrecord.bean.DemoReqData
import retrofit2.http.GET
import retrofit2.http.Path

interface DataAPI {
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId:Int): DemoReqData
}