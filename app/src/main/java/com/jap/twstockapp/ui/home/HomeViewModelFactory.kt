package com.jap.twstockapp.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.network.UpdateDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class HomeViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                app = application,
                stockinformationrepository = StockInformationRepository(
                    updatedataSource = UpdateDataSource()
            )) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}