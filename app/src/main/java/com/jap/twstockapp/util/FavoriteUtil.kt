package com.jap.twstockapp.util

import android.content.Context
import android.util.Log
import com.jap.twstockapp.roomdb.AppDatabase
import com.jap.twstockapp.roomdb.Favorite
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.dashboard.DashboardViewModel


class FavoriteUtil(applicationContext : Context){
    val db = AppDatabase.getInstance(applicationContext)


    fun get_all_favorite(dashboardViewModel: DashboardViewModel){
        Thread {
            val list_favorites = db.FavoriteDao().getAll()
            var temp_array = arrayListOf<Favorite>()
            for(i in list_favorites){
                temp_array.add(i)
            }
            dashboardViewModel._favorite.postValue(temp_array)
            AppDatabase.destroyInstance()
        }.start()
    }

    fun add_favorite(StockNo : String,Name : String){
        Thread {
            Log.e("add_favorite",StockNo)
            db.FavoriteDao().insertAll(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
            DashboardFragment.dashboardViewModel.get_favorite()//更新
        }.start()
    }

    fun remove_favorite(StockNo : String,Name : String){
        Thread {
            db.FavoriteDao().delete(Favorite(StockNo,Name))
            AppDatabase.destroyInstance()
            DashboardFragment.dashboardViewModel.get_favorite()//更新
        }.start()
    }


}