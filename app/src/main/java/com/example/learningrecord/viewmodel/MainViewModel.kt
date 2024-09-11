package com.example.learningrecord.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.learningrecord.source.ADataSource

class MainViewModel:ViewModel() {
    fun getData() = Pager(PagingConfig(pageSize = 1,prefetchDistance = 10)){
        ADataSource()
    }.flow.cachedIn(viewModelScope) //将 Flow 缓存到 viewModelScope，以确保 Flow 数据在配置更改（例如屏幕旋转）时不会丢失，同时可以被多个 UI 组件共享

}