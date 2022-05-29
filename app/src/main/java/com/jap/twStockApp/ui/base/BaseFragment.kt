package com.jap.twStockApp.ui.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.ui.BaseViewModel

open class BaseFragment : Fragment() {

    var baseViewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        baseViewModel = ViewModelProvider(requireActivity(), BaseFragmentViewModelFactory())[BaseViewModel::class.java]
        super.onCreate(savedInstanceState)
    }
}