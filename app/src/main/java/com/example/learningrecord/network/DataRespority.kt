package com.example.learningrecord.network

import com.example.learningrecord.api.DataApi
import com.example.learningrecord.bean.DemoReqData

class DataRespority {

    private var netWork = RetrofitService.createService(DataApi::class.java)

    suspend fun loadData(pageId:Int):DemoReqData?{
            return try {
                netWork.getData(pageId)
            }catch (e:Exception){
                null
            }
    }





}