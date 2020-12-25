package com.jap.twstockapp


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.ui.notifications.NotificationsFragment
import java.util.*
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
        val navigation = findViewById<View>(R.id.nav_view) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        mStacks = HashMap<String, Stack<Fragment>>()
        mStacks!!.put(TAB_HOME, Stack<Fragment>())
        mStacks!!.put(TAB_DASHBOARD, Stack<Fragment>())
        mStacks!!.put(TAB_NOTIFICATIONS, Stack<Fragment>())

        val fragmentManager =
            this@MainActivity.supportFragmentManager
        val fragments =
            fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && mStacks!![TAB_HOME]!!.size == 0) {
                mStacks!![TAB_HOME]!!.push(fragment)
                Log.i("MainActivity","onCreateView mStacks"+mStacks.toString())
            }
        }
    }
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                Log.i("MainActivity","onCreateView mStacks"+mStacks.toString())
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
            if (tabId == TAB_DASHBOARD) {
                switchContent(getNowFragment(),DashboardFragment() , tabId ,true)
            } else if (tabId == TAB_NOTIFICATIONS) {
                switchContent(getNowFragment(),NotificationsFragment() , tabId ,true)
            }
        } else {
            switchContent(getNowFragment(),mStacks!![tabId]!!.lastElement() , tabId ,true)
        }
    }
    
    /**
     * 切换fragment
     * @param from 要隐藏的fragment
     * @param to 要显示的fragment
     */
    fun switchContent(
        from: Fragment?,
        to: Fragment,
        tag :String?,
        init : Boolean
    ) {
        if (init) mStacks!![tag]!!.push(to)

        if (from !== to) {
            val manager: FragmentManager = supportFragmentManager
            val transaction: FragmentTransaction = manager.beginTransaction()
            //此处必须要进行判断，因为同一个fragment只能被add一次，否则会发生异常
            if (!to.isAdded) {
                //未添加
                transaction.hide(from!!)
                transaction.add(R.id.nav_host_fragment, to)
                transaction.show(to).commit()
            } else {
                transaction.hide(from!!)
                transaction.show(to).commit()
            }
        }
    }

    fun getNowFragment() : Fragment?{
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragments: List<Fragment> = mFragmentManager.getFragments()
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) {
                    return fragment
                }
            }
        }
        return null

    }


}