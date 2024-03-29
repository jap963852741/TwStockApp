package com.jap.twStockApp.ui.condition

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.Repository.FavoritesRespository
import com.jap.twStockApp.Repository.roomdb.FavoriteDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ConditionViewModelFactory(val application: Application) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConditionViewModel::class.java)) {
            return ConditionViewModel(
                app = application,
                favoritesRespository = FavoritesRespository(
                    favoriteDataSource = FavoriteDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
