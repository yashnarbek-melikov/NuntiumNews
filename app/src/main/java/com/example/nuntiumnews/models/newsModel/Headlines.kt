package com.example.nuntiumnews.models.newsModel

import com.example.nuntiumnews.database.entity.Article
import java.io.Serializable

data class Headlines(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
) : Serializable