package com.jap.twStockApp.ui

import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jap.twStockApp.R
import com.jap.twStockApp.di.module.applicationModule
import com.jap.twStockApp.di.module.homeModule
import com.jap.twStockApp.extension.withAdaptedData
import com.jap.twStockApp.extension.withItemContent
import com.jap.twStockApp.ui.home.HomeFragmentTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        loadKoinModules(
            listOf(
                applicationModule,
                homeModule
            )
        )
    }


    @Test
    fun checkNavigation() {  // 33s
        val randomList = listOf(::checkHomeFragment, ::checkConditionFragment, ::checkFavoriteFragment)
        for (i in 1..9) {
            randomList[i % 3].invoke()
        }
    }

    private fun checkHomeFragment() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

    private fun checkConditionFragment() {
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.condition_fragment)).check(matches(isDisplayed()))
    }

    private fun checkFavoriteFragment() {
        onView(withId(R.id.navigation_favorites)).perform(click())
        onView(withId(R.id.fragment_favorites)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearch() {
        checkHomeFragment()
        val homeFragment = HomeFragmentTest()
        homeFragment.inputStockNo("2330")
        homeFragment.clickSearch()
        onView(withId(R.id.search_hint)).check(matches(not(isDisplayed()))) // check Gone
//        onData(CoreMatchers.instanceOf(String::class.java)).check(matches(isDisplayed()))
        //check that item is present in list at position 0
        onView(withId(R.id.re_view)).check(matches((withAdaptedData(withItemContent("2330")))))
//        onData(withItemContent("2330")).check()
//        onData(withItemContent("2330")).check(matches(isDisplayed()))// check data
//        onView(withText("2331")).check(doesNotExist())// check data


        homeFragment.inputStockNo("")
//        onView(withId(R.id.re_view)).check(matches(atPosition(0, withText("Test Text"))))
    }


}