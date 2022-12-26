package com.jap.twStockApp.extension

import android.view.View
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.TypeSafeMatcher


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


fun withAdaptedData(dataMatcher: Matcher<Any>): Matcher<View?> {
    return object : TypeSafeMatcher<View?>() {
        override fun describeTo(description: Description) {
            description.appendText("with class name: ")
            dataMatcher.describeTo(description)
        }

        override fun matchesSafely(view: View?): Boolean {
            if (view !is RecyclerView) return false
            val adapter = view.adapter
            for (i in 0 until (adapter?.itemCount ?: 0)) {
                if (dataMatcher.matches(adapter?.getItemViewType(i))) {
                    return true
                }
            }
            return false
        }
    }
}

