package com.jap.twstockapp.Repository.roomdb

import android.content.Context
import com.jap.twstockapp.R
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.Favorite
import com.jap.twstockapp.Repository.roomdb.TwStock
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.ui.home.UpdateResult
import com.jap.twstockinformation.StockUtil
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