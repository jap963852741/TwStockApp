package com.jap.twStockApp.Repository.roomdb

import io.reactivex.rxjava3.core.Observable

class FavoriteDataSource {

    fun getAllFavorite(): Observable<List<Favorite>> {

        val observable = Observable.create<List<Favorite>> {
            val favorites = AppDatabase.getInstance(null)?.FavoriteDao()?.getAll() ?: return@create
            it.onNext(favorites)
            it.onComplete()
        }

        return observable
    }
}
