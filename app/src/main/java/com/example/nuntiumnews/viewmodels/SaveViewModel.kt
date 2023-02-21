package com.example.nuntiumnews.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nuntiumnews.database.dao.NewsDao
import com.example.nuntiumnews.database.entity.Article
import javax.inject.Inject

class SaveViewModel @Inject constructor(private val newsDao: NewsDao): ViewModel() {

    fun addArticle(article: Article) {
        newsDao.addArticle(article)
    }

    fun deleteArticle(publishedAt: String) {
        newsDao.deleteArticle(publishedAt)
    }

    fun getIsLiked(publishedAt: String): Int {
        return newsDao.getIsLiked(publishedAt)
    }

    fun getAllArticle(): List<Article> {
        return newsDao.getAllArticle()
    }
}