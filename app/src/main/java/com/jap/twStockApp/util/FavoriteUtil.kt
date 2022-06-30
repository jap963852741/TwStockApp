package com.jap.twStockApp.util

import android.content.Context
import android.util.Log
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteUtil(applicationContext: Context?) {
    val db = AppDatabase.getInstance(applicationContext)

    suspend fun addFavorite(StockNo: String, Name: String): Boolean = withContext(Dispatchers.IO) {
        try {
            db?.FavoriteDao()?.insertAll(Favorite(StockNo, Name))
            true
        }catch (e : Exception) {
            Log.e(this.javaClass.name, e.toString())
            false
        }
    }

    suspend fun removeFavorite(StockNo: String, Name: String): Boolean = withContext(Dispatchers.IO) {
        try {
            db?.FavoriteDao()?.delete(Favorite(StockNo, Name))
            true
        }catch (e : Exception) {
            Log.e(this.javaClass.name, e.toString())
            false
        }
    }

    suspend fun removeFavorite(favorite: Favorite): Boolean = withContext(Dispatchers.IO) {
        try {
            db?.FavoriteDao()?.delete(favorite)
            true
        }catch (e : Exception) {
            Log.e(this.javaClass.name, e.toString())
            false
        }
    }
}
