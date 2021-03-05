package com.jap.twstockapp.di

import com.jap.twstockapp.di.condition.ConditionComponent
import com.jap.twstockapp.di.condition.ConditionModule
import com.jap.twstockapp.di.home.HomeComponent
import com.jap.twstockapp.di.home.HomeModule
import com.jap.twstockapp.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Yossi Segev on 11/11/2017.
 */
@Singleton
@Component(modules = [
    (AppModule::class)
])

interface MainComponent {
    fun plus(homeModule: HomeModule): HomeComponent
    fun plus(conditionModule: ConditionModule): ConditionComponent
}