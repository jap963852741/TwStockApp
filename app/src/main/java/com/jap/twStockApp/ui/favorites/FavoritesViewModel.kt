package com.jap.twStockApp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twStockApp.Repository.FavoritesRespository
import com.jap.twStockApp.Repository.roomdb.Favorite
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesViewModel(app: Application, private val favoritesRespository: FavoritesRespository) : AndroidViewModel(app) {
    val context = getApplication<Application>().applicationContext
    val _favorite = MutableLiveData<ArrayList<Favorite>>().apply {
        value = arrayListOf()
    }
    companion object {
        lateinit var favorites: ArrayList<Favorite>
    }
    val favorite: LiveData<ArrayList<Favorite>> = _favorite

    fun get_favorite() {
        val favorite_list = arrayListOf<Favorite>()
        val observer: Observer<List<Favorite>> = object : Observer<List<Favorite>> {
            override fun onNext(item: List<Favorite>) {
                for (fav in item) {
                    favorite_list.add(fav)
                }
            }
            override fun onError(e: Throwable) {
            }
            override fun onComplete() {
                _favorite.value = favorite_list
            }
            override fun onSubscribe(d: Disposable) {
            }
        }

        favoritesRespository.getAllFavorite(context)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
