package com.jap.twStockApp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.StockNoArrayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    app: Application,
    private val stockInformationRepository: StockInformationRepository
) : AndroidViewModel(app) {

    private val _updateResult = MutableLiveData<UpdateResult>()
    private val _stockInformation = MutableLiveData<ArrayList<String>?>()
    private val _stockNoArrayList = MutableLiveData<ArrayList<String>?>()
    private val _loadingDialog = MutableLiveData<Boolean>()

    val updateResult: LiveData<UpdateResult> = _updateResult
    val stockInformation: LiveData<ArrayList<String>?> = _stockInformation
    val stockNoArrayList: LiveData<ArrayList<String>?> = _stockNoArrayList
    val loadingDialog: LiveData<Boolean> = _loadingDialog

    init {
        getAllStockList()
    }

    private fun getAllStockList() = viewModelScope.launch { _stockNoArrayList.postValue(StockNoArrayRepository().newLoadInfo(getApplication<Application>().applicationContext)) }
    fun updateText(stockNo: String) = viewModelScope.launch { _stockInformation.postValue(stockInformationRepository.getStockData(getApplication<Application>().applicationContext, stockNo)) }
    fun newUpdateStockDb(loadingPercent: (Int) -> Unit, finishCallBack: (() -> Unit)? = null) = viewModelScope.launch(Dispatchers.IO) {
        _loadingDialog.postValue(true)
        stockInformationRepository.updateDB(loadingPercent)
        _loadingDialog.postValue(false)
        finishCallBack?.invoke()
    }
}
