package com.example.nuntiumnews.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.nuntiumnews.database.entity.Article

@Dao
interface NewsDao {

    @Insert(onConflict = REPLACE)
    fun addArticle(article: Article)

    @Query("DELETE FROM article WHERE publishedAt = :publishedAt")
    fun deleteArticle(publishedAt: String)

    @Query("select count(publishedAt) from article where publishedAt like :publishedAt")
    fun getIsLiked(publishedAt: String): Int

    @Query("select * from article")
    fun getAllArticle(): List<Article>
}