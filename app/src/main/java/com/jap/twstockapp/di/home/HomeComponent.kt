package com.jap.twstockapp.di.home

import com.jap.twstockapp.Repository.network.UpdateDataSource
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.ui.home.HomeViewModel
import dagger.Subcomponent

@HomeScope
@Subcomponent(modules = [HomeModule::class])

interface HomeComponent {
    fun inject(homeFragment: HomeFragment)
    fun inject(homeViewModel : HomeViewModel)
}