package com.example.nuntiumnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nuntiumnews.repository.NewsRepository

class RecommendedViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RecommendedViewModel::class.java)) {
            return RecommendedViewModel(newsRepository) as T
        }
        throw Exception("Error")
    }
}