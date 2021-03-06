package com.jap.twstockapp.ui.favorites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jap.twstockapp.Repository.FavoritesRespository
import com.jap.twstockapp.Repository.roomdb.FavoriteDataSource

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class FavoriteViewModelFactory(val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(
                app = application,
                favoritesRespository = FavoritesRespository(
                    favoriteDataSource = FavoriteDataSource()
            )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}