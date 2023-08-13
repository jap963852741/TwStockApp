package com.jap.twStockApp.Repository.roomdb

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteDataSource {
    suspend fun getNewAllFavorite(): List<Favorite>? = withContext(Dispatchers.IO){
        AppDatabase.getInstance()?.FavoriteDao()?.getAll()
    }
}
