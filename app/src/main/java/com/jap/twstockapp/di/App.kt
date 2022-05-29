package com.jap.twstockapp.di

import android.app.Application
import android.content.Context
import com.jap.twstockapp.di.condition.ConditionComponent
import com.jap.twstockapp.di.condition.ConditionModule
import com.jap.twstockapp.di.home.HomeComponent
import com.jap.twstockapp.di.home.HomeModule
import com.jap.twstockapp.di.modules.AppModule

class App : Application() {

    private lateinit var mainComponent: MainComponent
    private var homeComponent: HomeComponent? = null
    private var conditionComponent: ConditionComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(applicationContext, this))
            .build()
    }

    fun createHomeComponent(fragmentContext: Context): HomeComponent {
        homeComponent = mainComponent.plus(HomeModule(fragmentContext))
        return homeComponent!!
    }
    fun releaseHomeComponent() {
        homeComponent = null
    }

    fun createConditionComponent(): ConditionComponent {
        conditionComponent = mainComponent.plus(ConditionModule())
        return conditionComponent!!
    }
    fun releaseConditionComponent() {
        conditionComponent = null
    }
}
