package com.example.mymusicappcompose.application

import android.app.Application
import com.example.mymusicappcompose.data.local.LocalDataSource

class MyApplication : Application() {

    override fun onCreate() {
        LocalDataSource.init(this)
        super.onCreate()
    }
}