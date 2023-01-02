package com.jap.twStockApp.ui

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jap.twStockApp.Matcher.ToastMatcher
import com.jap.twStockApp.Matcher.waitForToast
import com.jap.twStockApp.R
import com.jap.twStockApp.di.module.applicationModule
import com.jap.twStockApp.di.module.homeModule
import com.jap.twStockApp.extension.waitForView
import com.jap.twStockApp.extension.withAdaptedData
import com.jap.twStockApp.ui.home.HomeFragmentTest
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
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
    private var decorView: View? = null


    @Before
    fun setup() {
        activityRule.scenario.onActivity { activity -> decorView = activity.window.decorView }

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
        onView(withId(R.id.re_view)).check(matches((withAdaptedData(CoreMatchers.equalTo("2330")))))  // check correct data
        onView(withId(R.id.re_view)).check(matches(not(withAdaptedData(CoreMatchers.equalTo("2331")))))  // check incorrect data

        homeFragment.inputStockNo("")
        homeFragment.clickSearch()
        onView(withId(R.id.search_hint)).check(matches(not(isDisplayed()))) // check Toast
//        onData(withItemContent("2330")).check()
//        onData(withItemContent("2330")).check(matches(isDisplayed()))// check data
//        onView(withText("2331")).check(doesNotExist())// check data


        homeFragment.inputStockNo("")
//        waitForView(withText("請輸入正確格式")).check(matches(isDisplayed()))
        onView(withText("請輸入正確格式")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
        //会弹出一个文本为clicked的Toast
        Thread.sleep(1000)
        onView(withText(R.string.please_input_correct_style)).inRoot(withDecorView(not(`is`(decorView)))).check(matches(isDisplayed()))
//        onView(withId(R.id.re_view)).check(matches(atPosition(0, withText("Test Text"))))
    }


}