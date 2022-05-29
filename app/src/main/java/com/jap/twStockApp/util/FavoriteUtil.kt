package com.jap.twStockApp.util

import android.content.Context
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.Favorite

class FavoriteUtil(applicationContext: Context) {
    val db = AppDatabase.getInstance(applicationContext)

    fun add_favorite(StockNo: String, Name: String) {
        Thread {
            db.FavoriteDao().insertAll(Favorite(StockNo, Name))
            AppDatabase.destroyInstance() // 防內存洩漏
        }.start()
    }

    fun remove_favorite(StockNo: String, Name: String) {
        Thread {
            db.FavoriteDao().delete(Favorite(StockNo, Name))
            AppDatabase.destroyInstance()
        }.start()
    }
}
