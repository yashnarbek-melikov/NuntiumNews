package com.example.nuntiumnews.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntiumnews.repository.NewsRepository
import com.example.nuntiumnews.utils.NewsResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    fun getSearch(category: String): StateFlow<NewsResource> {

        val stateFlow = MutableStateFlow<NewsResource>(NewsResource.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            newsRepository.getSearchData(category).catch {
                stateFlow.emit(NewsResource.Error(it.message ?: ""))
            }.collect {
                if (it.isSuccessful) {
                    stateFlow.emit(NewsResource.Success(it.body()))
                }
            }
        }
        return stateFlow
    }
}