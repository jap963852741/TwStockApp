package com.jap.twstockapp.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.roomdb.AppDatabase
import com.jap.twstockapp.roomdb.Favorite
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.dashboard.DashboardViewModel
import com.jap.twstockapp.ui.favorites.FavoritesViewModel


class FavoriteUtil(applicationContext : Context){
    val db = AppDatabase.getInstance(applicationContext)

    fun add_favorite(StockNo : String,Name : String){
        Thread {
            Log.e("add_favorite",StockNo)
            db.FavoriteDao().insertAll(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
//            DashboardFragment.dashboardViewModel.get_favorite()//更新
        }.start()
    }

    fun remove_favorite(StockNo : String,Name : String){
        Thread {
            db.FavoriteDao().delete(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
//            DashboardFragment.dashboardViewModel.get_favorite()//更新
        }.start()
    }


}