package com.jap.twStockApp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BaseViewModel(
) : ViewModel() {

    private var _homeFragmentSearchText: MutableLiveData<String?> = MutableLiveData()
    val homeFragmentSearchText: LiveData<String?> = _homeFragmentSearchText


    fun setHomeFragmentSearchText(s: String) {
        _homeFragmentSearchText.postValue(s)
    }
}
