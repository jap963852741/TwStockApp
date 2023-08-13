package com.jap.twStockApp.di.module

import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.ui.BaseViewModel
import com.jap.twStockApp.ui.home.HomeViewModel
import com.jap.twStockApp.ui.home.IHomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory<IHomeViewModel> { HomeViewModel(get()) }
    factory { BaseViewModel() }
}