package com.jap.twstockapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jap.twstockapp.roomdb.Favorite

class FavoritesViewModel : ViewModel() {

    val _favorite = MutableLiveData<ArrayList<Favorite>>().apply {
        value = arrayListOf()
    }
    val favorite: LiveData<ArrayList<Favorite>> = _favorite


}