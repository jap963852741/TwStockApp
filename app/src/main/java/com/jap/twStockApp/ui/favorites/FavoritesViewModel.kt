package com.jap.twStockApp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jap.twStockApp.Repository.FavoritesRespository
import com.jap.twStockApp.Repository.roomdb.Favorite
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FavoritesViewModel(private val favoritesRespository: FavoritesRespository) :
    ViewModel() {

    private val _favorite = MutableLiveData<ArrayList<Favorite>>()
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

        favoritesRespository.getAllFavorite()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
