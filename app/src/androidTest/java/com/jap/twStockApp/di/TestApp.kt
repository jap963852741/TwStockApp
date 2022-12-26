package com.jap.twStockApp.di

import android.app.Application
import com.jap.twStockApp.di.module.applicationModule
import com.jap.twStockApp.di.module.homeModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class TestApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                applicationModule,
                homeModule
            )
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}