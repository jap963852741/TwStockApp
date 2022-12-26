package com.jap.twStockApp.Repository.roomdb

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteDataSource {

    fun getAllFavorite(): Observable<List<Favorite>> {

        val observable = Observable.create<List<Favorite>> {
            val favorites = AppDatabase.getInstance()?.FavoriteDao()?.getAll() ?: return@create
            it.onNext(favorites)
            it.onComplete()
        }

        return observable
    }

    suspend fun getNewAllFavorite(): List<Favorite>? = withContext(Dispatchers.IO){
        AppDatabase.getInstance()?.FavoriteDao()?.getAll()
    }
}
