package com.example.nuntiumnews.utils

import com.example.nuntiumnews.models.newsModel.Headlines

sealed class NewsResource {

    object Loading : NewsResource()

    data class Success(val headlines: Headlines?) : NewsResource()

    data class Error(val message: String) : NewsResource()

}