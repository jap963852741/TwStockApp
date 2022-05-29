package com.jap.twStockApp.ui

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.ActivityMainBinding
import com.jap.twStockApp.util.FragmentSwitchUtil

class MainActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivityMainBinding
    private lateinit var fragmentutil: FragmentSwitchUtil

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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentutil = FragmentSwitchUtil(fragmanager = supportFragmentManager).getInstance()
    }
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        fragmentutil.selectedTab(
                            fragmentutil.TAB_HOME
                        )
                        return true
                    }
                    R.id.navigation_dashboard -> {
                        fragmentutil.selectedTab(
                            fragmentutil.TAB_DASHBOARD
                        )
                        return true
                    }
                    R.id.navigation_favorites -> {
                        fragmentutil.selectedTab(
                            fragmentutil.TAB_NOTIFICATIONS
                        )
                        return true
                    }
                }
                return false
            }
        }
}
