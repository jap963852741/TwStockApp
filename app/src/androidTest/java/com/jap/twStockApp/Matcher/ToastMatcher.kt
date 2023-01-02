package com.jap.twStockApp.Matcher

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import androidx.test.espresso.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun waitForToast(toastMatcher: TypeSafeMatcher<Root?>, waitMillis: Int = 5000, waitMillisPerTry: Long = 100): ViewInteraction {
    val maxTries = waitMillis / waitMillisPerTry.toInt()
    var tries = 0
    for (i in 0..maxTries) {
        try {
            // Track the amount of times we've tried
            tries++
            // Search the root for the view
            return Espresso.onView(ViewMatchers.isRoot()).perform(searchFor(toastMatcher))
            // If we're here, we found our view. Now return it
        } catch (e: Exception) {
            if (tries == maxTries) throw e
            Thread.sleep(waitMillisPerTry)
        }
    }
    throw Exception("Error finding a view matching $toastMatcher")
}

fun searchFor(matcher: TypeSafeMatcher<Root?>): ViewAction {

    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
        override fun getDescription(): String = "searching for view $matcher in the root view"
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
                .build()
        }
    }
}

class ToastMatcher : TypeSafeMatcher<Root?>() {

    override fun describeTo(description: Description?) {
        description?.appendText("There is toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val type: Int? = item?.windowLayoutParams?.get()?.type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = item.decorView.windowToken
            val appToken: IBinder = item.decorView.applicationWindowToken
            if (windowToken === appToken) {
                return true
            }
        }
        return false
    }
}
