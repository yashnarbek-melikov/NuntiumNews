package com.example.nuntiumnews

import android.app.Application
import com.example.nuntiumnews.di.component.AppComponent
import com.example.nuntiumnews.di.component.DaggerAppComponent
import com.example.nuntiumnews.di.module.DatabaseModule
import com.yariksoffice.lingver.Lingver

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, "en")
        appComponent = DaggerAppComponent.builder().databaseModule(DatabaseModule(applicationContext)).build()
    }
}