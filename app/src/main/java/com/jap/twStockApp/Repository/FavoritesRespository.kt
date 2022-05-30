package com.jap.twStockApp.Repository

import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.FavoriteDataSource
import io.reactivex.rxjava3.core.Observable

class FavoritesRespository(val favoriteDataSource: FavoriteDataSource) {
    fun getAllFavorite(): Observable<List<Favorite>> {
        return favoriteDataSource.getAllFavorite()
    }
}
