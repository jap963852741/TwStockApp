package com.jap.twStockApp.ui.start

import android.os.Bundle
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil

class StartFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentSwitchUtil.getInstance(parentFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
    }
}
