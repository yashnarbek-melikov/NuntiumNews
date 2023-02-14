package com.example.nuntiumnews.retrofit

import com.example.nuntiumnews.models.newsModel.Headlines
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getListData(
        @Query("q") category: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 30,
        @Query("apiKey") apiKey: String = "30d052d3e20544fca54c914168ea3da3"
    ): Response<Headlines>
}