package com.example.nuntiumnews.repository

import com.example.nuntiumnews.networking.ApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getNewsData(category: String) =
        flow { emit(apiService.getListData(category = category)) }

    suspend fun getSearchData(category: String) =
        flow { emit(apiService.getSearchData(category = category)) }
}