package com.jap.twstockapp.Repository.roomdb

import android.content.Context
import io.reactivex.rxjava3.core.Observable

class FavoriteDataSource {

    fun get_all_favorite(context: Context): Observable<List<Favorite>> {

        val observable = Observable.create<List<Favorite>> {
            val list_favorites = AppDatabase.getInstance(context).FavoriteDao().getAll()
            it.onNext(list_favorites)
            it.onComplete()
        }

        return observable
    }
}
