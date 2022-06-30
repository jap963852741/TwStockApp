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
    private val _favorite = MutableLiveData<ArrayList<Favorite>>()
    val favorite: LiveData<ArrayList<Favorite>> = _favorite

    fun getFavorite() {
        val favoriteList = arrayListOf<Favorite>()
        val observer: Observer<List<Favorite>> = object : Observer<List<Favorite>> {
            override fun onNext(item: List<Favorite>) {
                for (fav in item) {
                    favoriteList.add(fav)
                }
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                _favorite.value = favoriteList
            }

            override fun onSubscribe(d: Disposable) {
            }
        }

        favoritesRepository.getAllFavorite()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun removeFavorite(favorite: Favorite, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = favoriteUtil.removeFavorite(favorite)
        successListener.invoke(result)
    }
}


