package com.jap.twStockApp.di.home

import com.jap.twStockApp.ui.home.HomeFragment
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])

interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
//    fun inject(homeViewModel : HomeViewModel)
}
