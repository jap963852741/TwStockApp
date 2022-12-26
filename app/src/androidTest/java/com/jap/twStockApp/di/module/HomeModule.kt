package com.jap.twStockApp.di.module

import com.jap.twStockApp.ui.home.HomeViewModelTest
import com.jap.twStockApp.ui.home.IHomeViewModel
import org.koin.dsl.module

val homeModule = module {
    factory<IHomeViewModel> { HomeViewModelTest() }
}