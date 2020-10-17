package com.fristcode.task

import android.app.Application
import com.fristcode.task.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(listOf(appModule))
        }
    }
}