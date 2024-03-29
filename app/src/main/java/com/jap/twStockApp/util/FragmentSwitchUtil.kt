package com.jap.twStockApp.util

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twStockApp.R
import com.jap.twStockApp.ui.condition.ConditionFragment
import com.jap.twStockApp.ui.favorites.FavoritesFragment
import com.jap.twStockApp.ui.home.HomeFragment
import java.util.*

class FragmentSwitchUtil private constructor(private val context: FragmentActivity) {

    private var stacks: HashMap<String, Stack<Fragment>> = HashMap<String, Stack<Fragment>>().apply {
        this[TAB_HOME] = Stack<Fragment>()
        this[TAB_DASHBOARD] = Stack<Fragment>()
        this[TAB_NOTIFICATIONS] = Stack<Fragment>()
    }
    private var _currentTab: MutableLiveData<String?> = MutableLiveData(TAB_HOME)
    var currentTab: LiveData<String?> = _currentTab
    private val manager: FragmentManager?
        get() {
            if (context.supportFragmentManager.isDestroyed) return null
            return context.supportFragmentManager
        }

    companion object {
        @Volatile
        private var INSTANCE: FragmentSwitchUtil? = null
        const val TAB_HOME = "tab_home"
        const val TAB_DASHBOARD = "tab_dashboard"
        const val TAB_NOTIFICATIONS = "tab_notifications"

        fun init() {
            INSTANCE = null
        }

        fun getInstance(fragmentManager: FragmentActivity): FragmentSwitchUtil? {
            if (INSTANCE != null) return INSTANCE
            synchronized(FragmentSwitchUtil::class) {
                INSTANCE = FragmentSwitchUtil(fragmentManager)
                return INSTANCE
            }
        }
    }

    fun selectedTab(tabId: String) {
        when (tabId) {
            TAB_HOME -> switchContent(getNowFragment(), HomeFragment(), tabId, stacks[tabId]?.size == 0)
            TAB_DASHBOARD -> switchContent(getNowFragment(), ConditionFragment(), tabId, stacks[tabId]?.size == 0)
            TAB_NOTIFICATIONS -> switchContent(getNowFragment(), FavoritesFragment(), tabId, stacks[tabId]?.size == 0)
        }
        _currentTab.value = tabId
    }

    fun backNowTabAndGetStackSize(): Int {
        if (currentTab.value == null) return 1
        if (stacks[currentTab.value] == null || stacks[currentTab.value]?.size == 1) return 1
        val frag = stacks[currentTab.value]?.last() ?: return 1
        val transaction = manager?.beginTransaction()
        transaction?.remove(frag)
        transaction?.commit()
        stacks[currentTab.value]?.removeLast()
        return (stacks[currentTab.value]?.size ?: 0) + 1
    }

    fun initTab() {
        if (getNowFragmentFromManager() != null) return
        val frag = getNowFragmentFromManager() ?: HomeFragment()
        _currentTab.value = TAB_HOME
        stacks[TAB_HOME]?.push(frag)
        val transaction = manager?.beginTransaction()
        transaction?.add(R.id.nav_host_fragment, frag)?.commit()
    }

    private fun switchContent(from: Fragment?, to: Fragment, tag: String, init: Boolean) {
        if (from == null || from == to) return
        if (init) stacks[tag]?.push(to)
        val transaction = manager?.beginTransaction()
        transaction?.hide(from)
        if (!to.isAdded) transaction?.add(R.id.nav_host_fragment, to)?.commit()
        else transaction?.show(to)?.commit()
    }

    private fun getNowFragment(): Fragment? {
        if (currentTab.value == null || stacks[currentTab.value]?.size == 0) return null
        return stacks[currentTab.value]?.lastElement()
    }

    private fun getNowFragmentFromManager(): Fragment? {
        val fragments: List<Fragment> = manager?.fragments ?: return null
        if (fragments.isEmpty()) return null
        return fragments[fragments.size - 1]
    }
}
