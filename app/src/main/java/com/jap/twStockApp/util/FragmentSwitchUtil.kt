package com.jap.twStockApp.util

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jap.twStockApp.R
import com.jap.twStockApp.ui.condition.ConditionFragment
import com.jap.twStockApp.ui.favorites.FavoritesFragment
import java.util.*

class FragmentSwitchUtil(fragmanager: FragmentManager) {

    var mStacks: HashMap<String, Stack<Fragment>>? = null
    var mCurrentTab: String? = null
    val TAB_HOME = "tab_home"
    val TAB_DASHBOARD = "tab_dashboard"
    val TAB_NOTIFICATIONS = "tab_notifications"
    var manager: FragmentManager

    init {
        manager = fragmanager
        mStacks = HashMap<String, Stack<Fragment>>()
        mStacks!!.put(TAB_HOME, Stack<Fragment>())
        mStacks!!.put(TAB_DASHBOARD, Stack<Fragment>())
        mStacks!!.put(TAB_NOTIFICATIONS, Stack<Fragment>())
    }

    companion object {
        private var INSTANCE: FragmentSwitchUtil? = null
    }

    fun getInstance(): FragmentSwitchUtil {
        if (INSTANCE == null) {
            INSTANCE = FragmentSwitchUtil(manager)
        }
        return INSTANCE!!
    }

    fun destroyInstance() {
        if (INSTANCE != null) {
            INSTANCE = null
        }
    }

    fun selectedTab(tabId: String) {
        mCurrentTab = tabId
        if (mStacks!![tabId]!!.size == 0) {
            /*
               *    First time this tab is selected. So add first fragment of that tab.
               *    Dont need animation, so that argument is false.
               *    We are adding a new fragment which is not present in stack. So add to stack is true.
               */
            if (tabId == TAB_DASHBOARD) {
                switchContent(getNowFragment(), ConditionFragment(), tabId, true)
            } else if (tabId == TAB_NOTIFICATIONS) {
                switchContent(getNowFragment(), FavoritesFragment(), tabId, false)
            }
        } else {
            switchContent(getNowFragment(), mStacks!![tabId]!!.lastElement(), tabId, false)
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
        tag: String?,
        init: Boolean
    ) {

        if (init) mStacks!![tag]!!.push(to)
        if (from !== to) {
            val transaction: FragmentTransaction = manager.beginTransaction()
            // 此处必须要进行判断，因为同一个fragment只能被add一次，否则会发生异常
            if (!to.isAdded) {
                // 未添加
                transaction.hide(from!!)
                transaction.add(R.id.nav_host_fragment, to)
                transaction.show(to).commit()
            } else {
                transaction.hide(from!!)
                transaction.show(to).commit()
            }
        }
    }

    fun getNowFragment(): Fragment? {
        val fragments: List<Fragment> = manager.getFragments()
//        val i = fragments.size-1
        for (i in 0..fragments.size - 1) {
            val j = fragments.size - 1 - i
            if (fragments[j].isVisible) {
                return fragments[j]
            }
        }

        return null
    }

    fun replaceCateFragment(
        animType: Int,
        frag: Fragment?
    ) {
        val transaction: FragmentTransaction = manager.beginTransaction()
        if (animType == 1) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }
        if (animType == 0) {
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        }
        mStacks!![TAB_HOME]!!.push(frag)
        transaction.replace(R.id.nav_host_fragment, frag!!)
        Log.i("replaceCateFragment", " fragment :   " + frag.toString())
        transaction.commit()
    }
}
