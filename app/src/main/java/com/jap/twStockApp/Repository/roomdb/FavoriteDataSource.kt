package com.jap.twStockApp.Repository.roomdb

import android.content.Context
import io.reactivex.rxjava3.core.Observable

class FavoriteDataSource {

    fun getAllFavorite(context: Context): Observable<List<Favorite>> {

        val observable = Observable.create<List<Favorite>> {
            val favorites = AppDatabase.getInstance(context)?.FavoriteDao()?.getAll() ?: return@create
            it.onNext(favorites)
            it.onComplete()
        }

        return observable
    }
}
