package com.jap.twstockapp.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.Repository.FavoriteTaskFinish
import com.jap.twstockapp.Repository.FavoritesRespository
import com.jap.twstockapp.Repository.roomdb.Favorite

class FavoritesViewModel(app: Application) : AndroidViewModel(app){
    val context = getApplication<Application>().applicationContext
    val _favorite = MutableLiveData<ArrayList<Favorite>>().apply {
        value = arrayListOf()
    }
    companion object{
        lateinit var favorites: ArrayList<Favorite>
    }
    val favorite: LiveData<ArrayList<Favorite>> = _favorite

    fun get_favorite(){
        FavoritesRespository().loadInfo(context,object : FavoriteTaskFinish {
            override fun onFinish(data: ArrayList<Favorite>) {
                _favorite.postValue(data)
            }
        })
    }

}