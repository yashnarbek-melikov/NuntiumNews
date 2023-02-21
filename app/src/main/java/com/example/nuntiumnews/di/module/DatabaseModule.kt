package com.example.nuntiumnews.di.module

import android.content.Context
import androidx.room.Room
import com.example.nuntiumnews.database.AppDatabase
import com.example.nuntiumnews.database.dao.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "my_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(appDatabase: AppDatabase): NewsDao =
        appDatabase.newsDao()
}