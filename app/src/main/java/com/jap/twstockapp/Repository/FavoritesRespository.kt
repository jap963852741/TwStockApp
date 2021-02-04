package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.Favorite
import com.jap.twstockapp.Repository.roomdb.network.FavoriteDataSource
import com.jap.twstockapp.Repository.roomdb.network.UpdateDataSource
import com.jap.twstockapp.ui.home.UpdateResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

import java.util.concurrent.Executors

class FavoritesRespository(val favoriteDataSource: FavoriteDataSource) {
    fun getAllFavorite(context : Context) : Observable<List<Favorite>> {
        return favoriteDataSource.get_all_favorite(context = context)
    }
}
