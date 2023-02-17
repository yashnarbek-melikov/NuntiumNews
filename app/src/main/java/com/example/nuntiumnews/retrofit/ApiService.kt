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
        @Query("apiKey") apiKey: String = "41e12e0982d74e94b0f889dcf7b4362c"
    ): Response<Headlines>
}