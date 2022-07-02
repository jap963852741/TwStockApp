package com.jap.twStockApp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.ui.BaseViewModel

open class BaseFragment : Fragment() {

    var baseViewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        baseViewModel = ViewModelProvider(requireActivity(), BaseFragmentViewModelFactory())[BaseViewModel::class.java]
        super.onCreate(savedInstanceState)
    }

    protected fun closeKeyBoard(v: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v?.windowToken, 0)
    }
}
