package com.jap.twStockApp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModelTest : IHomeViewModel, ViewModel() {

    private val _updateResult = MutableLiveData<UpdateResult>()
    private val _stockInformation = MutableLiveData<ArrayList<String>?>()
    private val _stockNoArrayList = MutableLiveData<ArrayList<String>?>()
    private val _loadingDialog = MutableLiveData<Boolean>()

    override val updateResult: LiveData<UpdateResult> = _updateResult
    override val stockInformation: LiveData<ArrayList<String>?> = _stockInformation
    override val stockNoArrayList: LiveData<ArrayList<String>?> = _stockNoArrayList
    override val loadingDialog: LiveData<Boolean> = _loadingDialog


    override fun updateText(stockNo: String) {
        viewModelScope.launch {
            _stockInformation.postValue(arrayListOf("2330"))
        }
    }

    override fun newUpdateStockDb(loadingPercent: (Int) -> Unit, finishCallBack: (() -> Unit)?) {
    }
}