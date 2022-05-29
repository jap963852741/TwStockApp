package com.jap.twStockApp.di

import com.jap.twStockApp.di.condition.ConditionComponent
import com.jap.twStockApp.di.condition.ConditionModule
import com.jap.twStockApp.di.home.HomeComponent
import com.jap.twStockApp.di.home.HomeModule
import com.jap.twStockApp.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */
@Singleton
@Component(
    modules = [
        (AppModule::class)
    ]
)

interface MainComponent {
    fun plus(homeModule: HomeModule): HomeComponent
    fun plus(conditionModule: ConditionModule): ConditionComponent
//    fun plus(baseModule: BaseModule): BaseComponent
}
