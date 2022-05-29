package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.FavoriteDataSource
import io.reactivex.rxjava3.core.Observable

class FavoritesRespository(val favoriteDataSource: FavoriteDataSource) {
    fun getAllFavorite(context: Context): Observable<List<Favorite>> {
        return favoriteDataSource.get_all_favorite(context = context)
    }
}
