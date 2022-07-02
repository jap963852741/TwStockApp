package com.jap.twStockApp.ui.start

import android.os.Bundle
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil

class StartFragment : BaseFragment() {

    private var fragmentUtil :FragmentSwitchUtil? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentUtil = FragmentSwitchUtil.getInstance(parentFragmentManager)
        fragmentUtil?.selectedTab(FragmentSwitchUtil.TAB_HOME)
    }
}
