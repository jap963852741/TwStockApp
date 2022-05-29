package com.jap.twstockapp.di.home

import com.jap.twstockapp.ui.home.HomeFragment
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])

interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
//    fun inject(homeViewModel : HomeViewModel)
}
