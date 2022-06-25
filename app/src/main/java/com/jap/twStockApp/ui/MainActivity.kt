package com.jap.twStockApp.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
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
    private var fragmentUtil: FragmentSwitchUtil? = null
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
        navigation = viewbinding.navView
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        fragmentUtil = FragmentSwitchUtil.getInstance(supportFragmentManager)
        baseViewModel = ViewModelProvider(this, BaseFragmentViewModelFactory())[BaseViewModel::class.java]
    }

    private val onNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragmentUtil?.selectedTab(FragmentSwitchUtil.TAB_HOME)
                    return true
                }
                R.id.navigation_dashboard -> {
                    fragmentUtil?.selectedTab(FragmentSwitchUtil.TAB_DASHBOARD)
                    return true
                }
                R.id.navigation_favorites -> {
                    fragmentUtil?.selectedTab(FragmentSwitchUtil.TAB_NOTIFICATIONS)
                    return true
                }
            }
            return false
        }
    }
}
