package com.jap.twStockApp.ui.favorites

import android.app.Application
import androidx.lifecycle.*
import com.jap.twStockApp.Repository.FavoritesRespository
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.util.FavoriteUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class FavoritesViewModel(app: Application, private val favoritesRepository: FavoritesRespository) : AndroidViewModel(app) {

    private val favoriteUtil = FavoriteUtil(getApplication<Application>().applicationContext)
    private val _favorite = MutableLiveData<List<Favorite>>()
    val favorite: LiveData<List<Favorite>> = _favorite

    fun getFavoriteNew() = viewModelScope.launch { _favorite.value = favoritesRepository.getAllFavorite() }

    fun removeFavorite(favorite: Favorite, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = favoriteUtil.removeFavorite(favorite)
        successListener.invoke(result)
    }
}


