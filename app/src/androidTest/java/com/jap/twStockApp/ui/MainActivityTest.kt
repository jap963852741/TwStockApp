package com.jap.twStockApp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jap.twStockApp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFragmentSwitch() {  // 33s
        val randomList = listOf(::checkHomeFragment, ::checkConditionFragment, ::checkFavoriteFragment)
        for (i in 1 .. 100) {
            randomList[Random.nextInt(3)].invoke()
        }
    }

    fun checkHomeFragment() {
        onView(withId(R.id.navigation_home)).perform(click())
        onView(withId(R.id.homefragment)).check(matches(isDisplayed()))
    }

    fun checkConditionFragment() {
        onView(withId(R.id.navigation_dashboard)).perform(click())
        onView(withId(R.id.condition_fragment)).check(matches(isDisplayed()))
    }

    fun checkFavoriteFragment() {
        onView(withId(R.id.navigation_favorites)).perform(click())
        onView(withId(R.id.fragment_favorites)).check(matches(isDisplayed()))
    }
}