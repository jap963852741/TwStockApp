package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.Repository.roomdb.Favorite
import com.jap.twstockapp.Repository.roomdb.FavoriteDataSource
import io.reactivex.rxjava3.core.Observable

class FavoritesRespository(val favoriteDataSource: FavoriteDataSource) {
    fun getAllFavorite(context : Context) : Observable<List<Favorite>> {
        return favoriteDataSource.get_all_favorite(context = context)
    }
}
