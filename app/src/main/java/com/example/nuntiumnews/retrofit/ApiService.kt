package com.example.nuntiumnews.retrofit

import com.example.nuntiumnews.models.newsModel.Headlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getListData(
        @Query("q") category: String,
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = "31a3888bc30d42e2ac637ee0258c14ed"
    ): Response<Headlines>
}