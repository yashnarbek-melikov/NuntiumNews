package com.example.nuntiumnews.repository

import com.example.nuntiumnews.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class NewsRepository(private val apiService: ApiService) {

    suspend fun getNewsData(category: String) =
        flow { emit(apiService.getListData(category = category)) }
}