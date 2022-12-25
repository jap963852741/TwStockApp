package com.jap.twStockApp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.databinding.ActivityMainBinding
import com.jap.twStockApp.ui.base.BaseFragmentViewModelFactory
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.ToastUtil

class MainActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityMainBinding
    var baseViewModel: BaseViewModel? = null
    private val leaveToast by lazy { Toast(this) }
    private var leaveToastShowing = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentSwitchUtil.init()
        leaveToast.setText("click again you will leave app")
        leaveToast.addCallback(object : Toast.Callback() {
            override fun onToastShown() {
                leaveToastShowing = true
                super.onToastShown()
            }

            override fun onToastHidden() {
                leaveToastShowing = false
                super.onToastHidden()
            }
        })
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Statusbar 轉為深色
        viewbinding.myBottomBar.navigationHomeEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_HOME) }
        viewbinding.myBottomBar.navigationDashboardEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_DASHBOARD) }
        viewbinding.myBottomBar.navigationFavoritesEvent = { FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_NOTIFICATIONS) }

        FragmentSwitchUtil.getInstance(this)?.currentTab?.observe(this) {
            if (it == null) return@observe
            when (it) {
                FragmentSwitchUtil.TAB_HOME -> viewbinding.myBottomBar.chooseHome()
                FragmentSwitchUtil.TAB_DASHBOARD -> viewbinding.myBottomBar.chooseDashboard()
                FragmentSwitchUtil.TAB_NOTIFICATIONS -> viewbinding.myBottomBar.chooseFavorite()
            }
        }

        baseViewModel = ViewModelProvider(this, BaseFragmentViewModelFactory())[BaseViewModel::class.java]
        FragmentSwitchUtil.getInstance(this)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
    }

    override fun onBackPressed() {
        if (FragmentSwitchUtil.getInstance(this)?.backNowTabAndGetStackSize() == 1) {
            if (leaveToastShowing) finish()
            else leaveToast.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
