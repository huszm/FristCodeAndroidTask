package com.fristcode.task

import android.app.Application
import com.fristcode.task.module.appModule
import com.fristcode.task.module.retrofitServiceModule
import com.fristcode.task.module.roomModule
import com.fristcode.task.module.viewModelModule
import com.fristcode.task.utils.DomainIntegration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        DomainIntegration.with(this)

        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(listOf(appModule , roomModule , retrofitServiceModule ,viewModelModule , ))
        }
    }
}