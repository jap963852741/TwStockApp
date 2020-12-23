package com.jap.twstockapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.MainActivity
import com.jap.twstockapp.roomdb.MyStockUtil

class HomeViewModel(app: Application) : AndroidViewModel(app){

    val _text = MutableLiveData<ArrayList<String?>>().apply {
        value = arrayListOf("")
    }

    val _StockNoArrayList = MutableLiveData<ArrayList<String>>().apply {
        value = arrayListOf("")
        MyStockUtil(getApplication<Application>().applicationContext).getAdapter(value)
    }
    val text: LiveData<ArrayList<String?>> = _text
    val StockNoArrayList: LiveData<ArrayList<String>> = _StockNoArrayList


    fun update_text(StockNo : String){
        MyStockUtil(getApplication<Application>().applicationContext).get_stock_information(_text,StockNo)
    }

}