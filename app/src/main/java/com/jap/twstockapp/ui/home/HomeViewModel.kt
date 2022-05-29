package com.jap.twstockapp.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.R
import com.jap.twstockapp.Repository.OnTaskFinish
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.StockNoArrayRepository
import com.jap.twstockapp.util.dialog.LoadingDialog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
    app: Application,
    private val stockInformationRepository: StockInformationRepository
) : AndroidViewModel(app) {
    val context = getApplication<Application>().applicationContext!!

    private val _updateResult = MutableLiveData<UpdateResult>()
    private val _StockInformation = MutableLiveData<ArrayList<String>>()
    private val _StockNoArrayList = MutableLiveData<ArrayList<String>>().apply {
        StockNoArrayRepository()
            .loadInfo(
                context,
                object :
                    OnTaskFinish {
                    override fun onFinish(data: ArrayList<String>) {
                        setStockNoArrayList(data)
                    }
                }
            )
    }

    val updateResult: LiveData<UpdateResult> = _updateResult
    val stockInformation: LiveData<ArrayList<String>> = _StockInformation
    val stockNoArrayList: LiveData<ArrayList<String>> = _StockNoArrayList

    fun updateText(StockNo: String) {
        stockInformationRepository
            .loadInfo(
                context, StockNo,
                object :
                    OnTaskFinish {
                    override fun onFinish(data: ArrayList<String>) {
                        _StockInformation.postValue(data)
                    }
                }
            )
    }

    fun setStockNoArrayList(list: ArrayList<String>) {
        this._StockNoArrayList.postValue(list)
    }

    fun updateStockDb(loadingDialog: LoadingDialog) {

        val observer: Observer<UpdateResult> = object : Observer<UpdateResult> {
            override fun onNext(item: UpdateResult) {
                _updateResult.value = UpdateResult(success = R.string.update_success)
            }
            override fun onError(e: Throwable) {
                _updateResult.value = UpdateResult(error = R.string.update_failed)
                Log.e("onError", e.toString())
            }
            override fun onComplete() {
                loadingDialog.dismiss()
            }
            override fun onSubscribe(d: Disposable) {
            }
        }

        stockInformationRepository.updateAllInformation(context, loadingDialog)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
