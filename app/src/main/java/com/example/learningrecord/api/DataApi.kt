package com.example.learningrecord.api

import com.example.learningrecord.bean.DemoReqData
import retrofit2.http.GET
import retrofit2.http.Path

interface DataApi {
    /**
     * 获取数据
     */
    @GET("wenda/list/{pageId}/json")
    suspend fun getData(@Path("pageId") pageId:Int): DemoReqData
}