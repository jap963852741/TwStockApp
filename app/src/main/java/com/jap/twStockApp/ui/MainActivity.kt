package com.jap.twStockApp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jap.twStockApp.databinding.ActivityMainBinding
import com.jap.twStockApp.ui.base.BaseFragmentViewModelFactory
import com.jap.twStockApp.util.FragmentSwitchUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var baseViewModel: BaseViewModel? = null
    private var leaveToast: Toast? = null
    private var leaveToastShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentSwitchUtil.init()
        leaveToast = Toast.makeText(this, "click again you will leave app", Toast.LENGTH_SHORT)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Statusbar 轉為深色
        viewBinding.myBottomBar.navigationHomeEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_HOME) }
        viewBinding.myBottomBar.navigationDashboardEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_DASHBOARD) }
        viewBinding.myBottomBar.navigationFavoritesEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_NOTIFICATIONS) }
        setContentView(viewBinding.root)

        FragmentSwitchUtil.getInstance(this)?.currentTab?.observe(this) {
            if (it == null) return@observe
            when (it) {
                FragmentSwitchUtil.TAB_HOME -> viewBinding.myBottomBar.chooseHome()
                FragmentSwitchUtil.TAB_DASHBOARD -> viewBinding.myBottomBar.chooseDashboard()
                FragmentSwitchUtil.TAB_NOTIFICATIONS -> viewBinding.myBottomBar.chooseFavorite()
            }
        }

        baseViewModel = ViewModelProvider(this, BaseFragmentViewModelFactory())[BaseViewModel::class.java]

    }

    override fun onResume() {
        super.onResume()

        FragmentSwitchUtil.getInstance(this)?.initTab()
    }

    override fun onBackPressed() {
        if (FragmentSwitchUtil.getInstance(this)?.backNowTabAndGetStackSize() == 1) {
            if (leaveToastShowing) finish()
            else {
                leaveToastTimeClick()
                leaveToast?.show()
            }
        }
    }

    private fun leaveToastTimeClick(): Job = lifecycleScope.launch(Dispatchers.IO) {
        val toast = leaveToast ?: return@launch
        val times = if (toast.duration == Toast.LENGTH_SHORT) 2000L else 3500L
        leaveToastShowing = true
        delay(times)
        leaveToastShowing = false
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
