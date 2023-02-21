package com.example.nuntiumnews.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nuntiumnews.database.dao.NewsDao
import com.example.nuntiumnews.database.entity.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun newsDao(): NewsDao
}