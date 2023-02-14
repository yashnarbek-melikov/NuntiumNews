package com.example.nuntiumnews.models.newsModel

import com.example.nuntiumnews.models.newsModel.Article

data class Headlines(
    val articles: List<Article>?,
    val status: String?,
    val totalResults: Int?
)