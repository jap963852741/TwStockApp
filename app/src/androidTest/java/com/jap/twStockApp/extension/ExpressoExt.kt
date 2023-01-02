package com.jap.twStockApp.extension

import android.util.Log
import android.view.View
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import com.jap.twStockApp.ui.base.RecyclerViewItem
import org.hamcrest.BaseMatcher
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.TypeSafeMatcher
import java.lang.Thread.sleep

/**
 * Perform action of implicitly waiting for a certain view.
 * This differs from EspressoExtensions.searchFor in that,
 * upon failure to locate an element, it will fetch a new root view
 * in which to traverse searching for our @param match
 *
 * @param viewMatcher ViewMatcher used to find our view
 */
fun waitForView(viewMatcher: Matcher<View>, waitMillis: Int = 5000, waitMillisPerTry: Long = 100): ViewInteraction {
    val maxTries = waitMillis / waitMillisPerTry.toInt()
    var tries = 0
    for (i in 0..maxTries) {
        try {
            // Track the amount of times we've tried
            tries++
            // Search the root for the view
            onView(isRoot()).perform(searchFor(viewMatcher))
            // If we're here, we found our view. Now return it
            return onView(viewMatcher)
        } catch (e: Exception) {
            if (tries == maxTries) throw e
            sleep(waitMillisPerTry)
        }
    }
    throw Exception("Error finding a view matching $viewMatcher")
}

/**
 * Perform action of waiting for a certain view within a single root view
 * @param matcher Generic Matcher used to find our view
 */
fun searchFor(matcher: Matcher<View>): ViewAction {

    return object : ViewAction {

        override fun getConstraints(): Matcher<View> {
            return isRoot()
        }

        override fun getDescription(): String {
            return "searching for view $matcher in the root view"
        }

        override fun perform(uiController: UiController, view: View) {
            var tries = 0
            val childViews: Iterable<View> = TreeIterables.breadthFirstViewTraversal(view)
            // Look for the match in the tree of childviews
            childViews.forEach {
                tries++
                if (matcher.matches(it)) {
                    // found the view
                    return
                }
            }
            throw NoMatchingViewException.Builder()
                .withRootView(view)
                .withViewMatcher(matcher)
                .build()
        }
    }
}

fun withItemContent(expectedText: String?): Matcher<Any> {
    checkNotNull(expectedText)
    return withItemContent(equalTo(expectedText))
}

fun withItemContent(itemTextMatcher: Matcher<Any>) = object : BoundedMatcher<Any, Map<*, *>>(Map::class.java) {
    override fun matchesSafely(map: Map<*, *>): Boolean {
        return hasEntry(equalTo("STR"), itemTextMatcher).matches(map)
    }

    override fun describeTo(description: Description) {
        description.appendText("with item content: ")
        itemTextMatcher.describeTo(description)
    }
}


fun withAdaptedData(dataMatcher: Matcher<*>): Matcher<View?> {
    return object : TypeSafeMatcher<View?>() {
        override fun describeTo(description: Description) {
            description.appendText("with class name: ")
            dataMatcher.describeTo(description)
        }

        override fun matchesSafely(view: View?): Boolean {
            if (view == null || view !is RecyclerView) return false
            val adapter = view.adapter
            if (adapter !is RecyclerViewItem) return false

            for (i in 0 until (adapter.getSize())) {
                if (dataMatcher.matches(adapter.getItem(i))) return true
            }
            return false
        }
    }
}

