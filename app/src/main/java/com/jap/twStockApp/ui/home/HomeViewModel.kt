package com.jap.twStockApp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.StockNoArrayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface IHomeViewModel {
    val updateResult: LiveData<UpdateResult>
    val stockInformation: LiveData<ArrayList<String>?>
    val stockNoArrayList: LiveData<ArrayList<String>?>
    val loadingDialog: LiveData<Boolean>
    fun updateText(stockNo: String)
    fun newUpdateStockDb(loadingPercent: (Int) -> Unit, finishCallBack: (() -> Unit)? = null)
}

class HomeViewModel(private val stockInformationRepository: StockInformationRepository) : ViewModel(), IHomeViewModel {

    private val _updateResult = MutableLiveData<UpdateResult>()
    private val _stockInformation = MutableLiveData<ArrayList<String>?>()
    private val _stockNoArrayList = MutableLiveData<ArrayList<String>?>()
    private val _loadingDialog = MutableLiveData<Boolean>()

    override val updateResult: LiveData<UpdateResult> = _updateResult
    override val stockInformation: LiveData<ArrayList<String>?> = _stockInformation
    override val stockNoArrayList: LiveData<ArrayList<String>?> = _stockNoArrayList
    override val loadingDialog: LiveData<Boolean> = _loadingDialog

    init {
        getAllStockList()
    }

    private fun getAllStockList() = viewModelScope.launch { _stockNoArrayList.postValue(StockNoArrayRepository().newLoadInfo()) }
    override fun updateText(stockNo: String) {
        viewModelScope.launch { _stockInformation.postValue(stockInformationRepository.getStockData(stockNo)) }
    }

    override fun newUpdateStockDb(loadingPercent: (Int) -> Unit, finishCallBack: (() -> Unit)?) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingDialog.postValue(true)
            stockInformationRepository.updateDB(loadingPercent)
            _loadingDialog.postValue(false)
            finishCallBack?.invoke()
        }
    }
}
