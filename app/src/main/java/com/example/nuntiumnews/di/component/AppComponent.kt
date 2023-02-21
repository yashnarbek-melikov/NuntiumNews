package com.example.nuntiumnews.di.component

import com.example.nuntiumnews.di.module.DatabaseModule
import com.example.nuntiumnews.di.module.NetworkModule
import com.example.nuntiumnews.ui.after.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(recommendedFragment: RecommendedFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(bookmarkFragment: BookmarkFragment)

    fun inject(articleFragment: ArticleFragment)
}