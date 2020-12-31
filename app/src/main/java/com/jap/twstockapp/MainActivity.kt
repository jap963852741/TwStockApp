package com.jap.twstockapp


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.util.FragmentSwitchUtil
import com.jap.twstockapp.util.FragmentSwitchUtil.Companion.TAB_DASHBOARD
import com.jap.twstockapp.util.FragmentSwitchUtil.Companion.TAB_HOME
import com.jap.twstockapp.util.FragmentSwitchUtil.Companion.TAB_NOTIFICATIONS
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {


//
    companion object{
        lateinit var fragmentutil : FragmentSwitchUtil
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("MainActivity","onCreateView savedInstanceState"+savedInstanceState.toString())
        fragmentutil = FragmentSwitchUtil(this)
        fragmentutil.mStacks = HashMap<String, Stack<Fragment>>()
        fragmentutil.mStacks!!.put(TAB_HOME, Stack<Fragment>())
        fragmentutil.mStacks!!.put(TAB_DASHBOARD, Stack<Fragment>())
        fragmentutil.mStacks!!.put(TAB_NOTIFICATIONS, Stack<Fragment>())


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //Statusbar 轉為深色
//    window.statusBarColor =  View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val navigation = findViewById<View>(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)






    }
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
//                Log.i("MainActivity","onCreateView mStacks"+fragmentutil.mStacks.toString())
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        fragmentutil.selectedTab(TAB_HOME)
                        return true
                    }
                    R.id.navigation_dashboard -> {
                        fragmentutil.selectedTab(TAB_DASHBOARD)
                        return true
                    }
                    R.id.navigation_notifications -> {
                        fragmentutil.selectedTab(TAB_NOTIFICATIONS)
                        return true
                    }
                }
                return false
            }
        }

    fun GoHome(c :String){
        fragmentutil.selectedTab(TAB_HOME)
        (fragmentutil.mStacks!![TAB_HOME]!!.lastElement() as HomeFragment).stocktext.setText(c)
    }

}