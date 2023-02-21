package com.example.nuntiumnews.networking

import com.example.nuntiumnews.models.newsModel.Headlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getListData(
        @Query("q") category: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = "ec53550bda604e4dbb8b95a8027b5e3a"
    ): Response<Headlines>

    @GET("everything")
    suspend fun getSearchData(
        @Query("q") category: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 50,
        @Query("apiKey") apiKey: String = "ec53550bda604e4dbb8b95a8027b5e3a"
    ): Response<Headlines>
}