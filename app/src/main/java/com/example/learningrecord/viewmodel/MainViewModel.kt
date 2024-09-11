package com.example.learningrecord.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.learningrecord.source.ADataSource

class MainViewModel:ViewModel() {
    fun getData() = Pager(PagingConfig(pageSize = 1,prefetchDistance = 10)){
        ADataSource()
    }.flow

}