package com.jap.twStockApp.di.module

import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.ui.home.HomeViewModel
import com.jap.twStockApp.ui.home.IHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    factory<IHomeViewModel> { HomeViewModel(get()) }

    factory<StockInformationRepository> { StockInformationRepository(get()) }
    factory<UpdateDataSource> { UpdateDataSource() }
}