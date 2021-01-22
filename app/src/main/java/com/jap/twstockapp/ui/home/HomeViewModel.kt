package com.jap.twstockapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.Repository.OnTaskFinish
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.StockNoArrayRepository

class HomeViewModel(app: Application) : AndroidViewModel(app){
    val context = getApplication<Application>().applicationContext
    val _StockInformation = MutableLiveData<ArrayList<String>>()
    val _StockNoArrayList = MutableLiveData<ArrayList<String>>().apply {
        StockNoArrayRepository()
            .loadInfo(context,object :
                OnTaskFinish {
            override fun onFinish(data: ArrayList<String>) {
                set_StockNoArrayList(data)
            }
        })
    }
    val StockInformation: LiveData<ArrayList<String>> = _StockInformation
    val StockNoArrayList: LiveData<ArrayList<String>> = _StockNoArrayList

    fun update_text(StockNo : String){
        StockInformationRepository()
            .loadInfo(context,StockNo,object :
            OnTaskFinish {
            override fun onFinish(data: ArrayList<String>) {
                _StockInformation.postValue(data)
            }
        })

    }

    fun set_StockNoArrayList(list : ArrayList<String>){
        this._StockNoArrayList.postValue(list)
    }

}