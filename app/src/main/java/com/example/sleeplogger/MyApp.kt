package com.example.sleeplogger

import android.content.Context
import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MyApp : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}