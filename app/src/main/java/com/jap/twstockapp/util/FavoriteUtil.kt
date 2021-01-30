package com.jap.twstockapp.util

import android.content.Context
import android.util.Log
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.Favorite


class FavoriteUtil(applicationContext : Context){
    val db = AppDatabase.getInstance(applicationContext)

    fun add_favorite(StockNo : String,Name : String){
        Thread {
            Log.e("add_favorite",StockNo)
            db.FavoriteDao().insertAll(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
        }.start()
    }

    fun remove_favorite(StockNo : String,Name : String){
        Thread {
            db.FavoriteDao().delete(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
        }.start()
    }


}