package com.jap.twStockApp

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import com.jap.twStockApp.extension.searchFor
import org.hamcrest.Matcher


const val DEFAULT_TIMEOUT: Int = 10000
const val CHECK_INTERVAL: Long = 500
open class BaseView {


    protected fun waitFor(viewMatcher: Matcher<View>, timeout: Int = DEFAULT_TIMEOUT, interval: Long = CHECK_INTERVAL): ViewInteraction {
        val maxTries = timeout / interval.toInt()
        var tries = 0

        for (i in 0..maxTries) {
            try {
                tries++
                Espresso.onView(ViewMatchers.isRoot()).perform(searchFor(viewMatcher))
                return Espresso.onView(viewMatcher)
            } catch (e: Exception) {
                if (tries == maxTries) {
                    throw e
                }
                SystemClock.sleep(interval)
            }
        }

        throw Exception("Error finding a view matching $viewMatcher")
    }
}