package com.jap.twstockapp.di

import android.app.Application
import com.jap.twstockapp.di.home.HomeComponent
import com.jap.twstockapp.di.home.HomeModule
import com.jap.twstockapp.di.modules.AppModule


class App: Application() {

    private lateinit var mainComponent: MainComponent
    private var homeComponent : HomeComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
                .appModule(AppModule(applicationContext,this))
                .build()
    }

    fun createHomeComponent(): HomeComponent {
        homeComponent = mainComponent.plus(HomeModule())
        return homeComponent!!
    }
    fun releaseHomeComponent() {
        homeComponent = null
    }


}