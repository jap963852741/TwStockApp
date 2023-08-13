package com.jap.twStockApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.util.SingleLiveData
import kotlinx.coroutines.launch

class BaseViewModel : ViewModel() {
    private var _homeFragmentSearchText: MutableLiveData<String?> = MutableLiveData()
    val homeFragmentSearchText: LiveData<String?> = _homeFragmentSearchText

    private var _loadingBarPercentLiveData: MutableLiveData<Float> = MutableLiveData(0f)
    val loadingBarPercentLiveData: LiveData<Float> = _loadingBarPercentLiveData

    private var _loadingFail: SingleLiveData<Unit?> = SingleLiveData()
    val loadingFail: LiveData<Unit?> = _loadingFail

    fun setHomeFragmentSearchText(s: String) {
        _homeFragmentSearchText.postValue(s)
    }

    fun updateAllData() = viewModelScope.launch {
        UpdateDataSource().updateDB({ finish -> _loadingBarPercentLiveData.postValue(finish / 100.toFloat()) },
            {_loadingFail.postValue(null)})
    }

}
