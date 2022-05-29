package com.jap.twStockApp.di

import android.app.Application
import android.content.Context
import com.jap.twStockApp.di.condition.ConditionComponent
import com.jap.twStockApp.di.condition.ConditionModule
import com.jap.twStockApp.di.home.HomeComponent
import com.jap.twStockApp.di.home.HomeModule
import com.jap.twStockApp.di.modules.AppModule

class App : Application() {

    private lateinit var mainComponent: MainComponent
    private var homeComponent: HomeComponent? = null
    private var conditionComponent: ConditionComponent? = null
//    private var baseComponent: BaseComponent? = null

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainComponent.builder()
            .appModule(AppModule(applicationContext, this))
            .build()
    }

    fun createHomeComponent(fragmentContext: Context): HomeComponent? {
        homeComponent = mainComponent.plus(HomeModule(fragmentContext))
        return homeComponent
    }
    fun releaseHomeComponent() {
        homeComponent = null
    }

    fun createConditionComponent(): ConditionComponent {
        conditionComponent = mainComponent.plus(ConditionModule())
        return conditionComponent!!
    }

//    fun createBaseFragmentComponent(): BaseComponent? {
//        baseComponent = mainComponent.plus(BaseModule())
//        return baseComponent
//    }


    fun releaseConditionComponent() {
        conditionComponent = null
    }
}
