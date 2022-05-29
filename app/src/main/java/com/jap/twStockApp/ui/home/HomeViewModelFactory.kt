package com.jap.twStockApp.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.Repository.StockInformationRepository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class HomeViewModelFactory(val application: Application, private val stockInformationRepository: StockInformationRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, stockInformationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
