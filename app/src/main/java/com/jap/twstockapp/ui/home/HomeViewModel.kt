package com.jap.twstockapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jap.twstockapp.R
import com.jap.twstockapp.Repository.OnTaskFinish
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.StockNoArrayRepository
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.TwStock
import com.jap.twstockinformation.StockUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(app : Application,private val stockinformationrepository : StockInformationRepository) : AndroidViewModel(app){
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

    private val _updateResult = MutableLiveData<UpdateResult>()
    val updateResult: LiveData<UpdateResult> = _updateResult

    val StockInformation: LiveData<ArrayList<String>> = _StockInformation
    val StockNoArrayList: LiveData<ArrayList<String>> = _StockNoArrayList

    fun update_text(StockNo : String){
        stockinformationrepository
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

    fun update_stock_db(){
        val observer: Observer<UpdateResult> = object : Observer<UpdateResult> {
            override fun onNext(item: UpdateResult) {
                _updateResult.value = UpdateResult(success =  R.string.update_success)
            }
            override fun onError(e: Throwable) {
                _updateResult.value =  UpdateResult(error = R.string.update_failed)
            }
            override fun onComplete() {
            }
            override fun onSubscribe(d: Disposable) {
            }
        }

        stockinformationrepository.UpdateAllInformation(context)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}