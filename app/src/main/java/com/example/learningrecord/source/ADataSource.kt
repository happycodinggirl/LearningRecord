package com.example.learningrecord.source

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.learningrecord.bean.DemoReqData
import com.example.learningrecord.network.DataRespority
import okio.IOException

class ADataSource: PagingSource<Int, DemoReqData.DataBean.DatasBean>() {
    override fun getRefreshKey(state: PagingState<Int, DemoReqData.DataBean.DatasBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DemoReqData.DataBean.DatasBean> {
            return try {

                val currentPage= params.key?:1
                val demoReqData=DataRespority().loadData(currentPage)
                Log.v("tt","currentPage is $currentPage")
                val nextPage =if (currentPage<demoReqData?.data?.pageCount?:0){
                    currentPage+1
                }else{
                    null
                }
                LoadResult.Page(prevKey = null, data = demoReqData!!.data!!.datas!!, nextKey = nextPage )
            }catch (e:Exception){
                if (e is IOException){
                    Log.e("--ee"," ----链接失败， e is ${e.message}")
                }
                LoadResult.Error(throwable = e)
            }
    }
}