package com.jap.twStockApp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.ActivityMainBinding
import com.jap.twStockApp.ui.base.BaseFragmentViewModelFactory
import com.jap.twStockApp.util.FragmentSwitchUtil

class MainActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityMainBinding
    var baseViewModel: BaseViewModel? = null

    companion object {
        lateinit var navigation: BottomNavigationView
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Statusbar 轉為深色
        viewbinding.myBottomBar.navigationHomeEvent = { FragmentSwitchUtil.getInstance(supportFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME) }
        viewbinding.myBottomBar.navigationDashboardEvent = { FragmentSwitchUtil.getInstance(supportFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_DASHBOARD) }
        viewbinding.myBottomBar.navigationFavoritesEvent = { FragmentSwitchUtil.getInstance(supportFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_NOTIFICATIONS) }
        baseViewModel = ViewModelProvider(this, BaseFragmentViewModelFactory())[BaseViewModel::class.java]
        FragmentSwitchUtil.getInstance(supportFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
    }

}
