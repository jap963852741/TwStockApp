package com.jap.twstockapp


import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jap.twstockapp.roomdb.MyStockUtil
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.ui.notifications.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private var mStacks: HashMap<String, Stack<Fragment>>? = null
    val TAB_HOME = "tab_home"
    val TAB_DASHBOARD = "tab_dashboard"
    val TAB_NOTIFICATIONS = "tab_notifications"

    private var mCurrentTab: String? = null
//
//    companion object{
////        lateinit var navController : NavController
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
    Log.i("MainActivity","onCreateView savedInstanceState"+savedInstanceState.toString())
    super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        状态栏文字颜色只能在Android6.0以上版本才能自定义修改 ，
//        但只有两种选择： 白色 （0）和 灰色（View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR）
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //Statusbar 轉為深色
//        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
//        navView.setupWithNavController(navController)
//        navController = findNavController(R.id.nav_host_fragment)
        val navigation = findViewById<View>(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mStacks = HashMap<String, Stack<Fragment>>()
        mStacks!!.put(TAB_HOME, Stack<Fragment>())
        mStacks!!.put(TAB_DASHBOARD, Stack<Fragment>())
        mStacks!!.put(TAB_NOTIFICATIONS, Stack<Fragment>())
    }
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.getItemId()) {
                    R.id.navigation_home -> {
                        selectedTab(TAB_HOME)
                        return true
                    }
                    R.id.navigation_dashboard -> {
                        selectedTab(TAB_DASHBOARD)
                        return true
                    }
                    R.id.navigation_notifications -> {
                        selectedTab(TAB_NOTIFICATIONS)
                        return true
                    }
                }
                return false
            }
        }

    private fun selectedTab(tabId: String) {
        mCurrentTab = tabId
        if (mStacks!![tabId]!!.size == 0) {
            /*
               *    First time this tab is selected. So add first fragment of that tab.
               *    Dont need animation, so that argument is false.
               *    We are adding a new fragment which is not present in stack. So add to stack is true.
               */
            if (tabId == TAB_HOME) {
                pushFragments(tabId, HomeFragment(), true)
            } else if (tabId == TAB_DASHBOARD) {
                pushFragments(tabId, DashboardFragment() ,  true)
            } else if (tabId == TAB_NOTIFICATIONS) {
                pushFragments(tabId, NotificationsFragment(), true)
            }
        } else {
            /*
               *    We are switching tabs, and target tab is already has atleast one fragment.
               *    No need of animation, no need of stack pushing. Just show the target fragment
               */
            pushFragments(tabId, mStacks!![tabId]!!.lastElement(), false)
        }
    }

    fun pushFragments(
        tag: String?,
        fragment: Fragment?,
        shouldAdd: Boolean
    ) {
        if (shouldAdd) mStacks!![tag]!!.push(fragment)
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, fragment!!)
        ft.commit()
    }

    fun popFragments() {
        /*
   *    Select the second last fragment in current tab's stack..
   *    which will be shown after the fragment transaction given below
   */
        val fragment =
            mStacks!![mCurrentTab]!!.elementAt(mStacks!![mCurrentTab]!!.size - 2)

        /*pop current fragment from stack.. */mStacks!![mCurrentTab]!!.pop()

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, fragment)
        ft.commit()
    }

    override fun onBackPressed() {
        if (mStacks!![mCurrentTab]!!.size == 1) {
            // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
            finish()
            return
        }
        /* Goto previous fragment in navigation stack of this tab */
        popFragments()
    }


}