package com.jap.twStockApp.Repository

import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.FavoriteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRespository(val favoriteDataSource: FavoriteDataSource) {
    suspend fun getAllFavorite(): List<Favorite>? = withContext(Dispatchers.IO) { favoriteDataSource.getNewAllFavorite() }
}
