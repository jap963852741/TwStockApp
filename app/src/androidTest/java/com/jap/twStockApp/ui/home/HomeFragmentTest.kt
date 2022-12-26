package com.jap.twStockApp.ui.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.jap.twStockApp.BaseView
import com.jap.twStockApp.R

class HomeFragmentTest: BaseView() {

    fun clickSearch() {
        Espresso.onView(withId(R.id.search)).perform(ViewActions.click())
    }

    fun inputStockNo(stockNo: String) {
        Espresso.onView(withId(R.id.auto_complete_text)).perform(ViewActions.replaceText(stockNo))
    }
}