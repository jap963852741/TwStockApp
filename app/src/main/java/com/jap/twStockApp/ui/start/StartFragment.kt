package com.jap.twStockApp.ui.start

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.FragmentHomeBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.ui.home.HomeAdapter
import com.jap.twStockApp.ui.home.HomeViewModel
import com.jap.twStockApp.ui.home.HomeViewModelFactory
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class StartFragment : BaseFragment() {

    private var fragmentUtil :FragmentSwitchUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentUtil = FragmentSwitchUtil.getInstance(parentFragmentManager)
        fragmentUtil?.selectedTab(FragmentSwitchUtil.TAB_HOME)
    }
}
