package com.jap.twStockApp.di

import android.app.Application
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.di.module.applicationModule
import com.jap.twStockApp.di.module.homeModule
import com.jap.twStockApp.di.module.viewModelModule
import com.jap.twStockApp.util.SharedPreference
import com.jap.twStockApp.util.SingleStockUtil
import com.jap.twStockApp.util.ToastUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {


    override fun onCreate() {
        super.onCreate()
        // init roomDB
        AppDatabase.appDataBaseInit(applicationContext)
        ToastUtil.init(applicationContext)
        SharedPreference.init(applicationContext)
        SingleStockUtil.init()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@App)
            // Load modules
            modules(
                applicationModule,
                homeModule,
                viewModelModule
            )
        }
    }
}
