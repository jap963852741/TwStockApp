package com.jap.twStockApp.util

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.Favorite
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FavoriteUtilTest {

    private val TAG = this.javaClass.name
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private var favoriteUtil: FavoriteUtil? = null

    @Before
    fun testInit() {
        favoriteUtil = FavoriteUtil(appContext)
    }

    @Test
    fun test() {
        val beginTime = System.currentTimeMillis()
        for (i in 1..10) {
            addTest()
            removeTest()
        }
        assertFalse(AppDatabase.getInstance(appContext)?.FavoriteDao()?.getAll()?.contains(Favorite("8099", "大世科")) == true)
        Log.i(TAG + this.javaClass.fields, (System.currentTimeMillis() - beginTime).toString())
    }

    private fun addTest() {
        var result: Boolean
        runBlocking {
            favoriteUtil?.removeFavorite("8099", "大世科")
            result = favoriteUtil?.addFavorite("8099", "大世科") == true
        }
        assertTrue(result)
        assertFalse(AppDatabase.getInstance(appContext)?.FavoriteDao()?.getAll()?.contains(Favorite("8099", "1234")) == true)
        assertFalse(AppDatabase.getInstance(appContext)?.FavoriteDao()?.getAll()?.contains(Favorite("1234", "大世科")) == true)
        assertTrue(AppDatabase.getInstance(appContext)?.FavoriteDao()?.getAll()?.contains(Favorite("8099", "大世科")) == true)
    }

    private fun removeTest() {
        var result: Boolean
        runBlocking {
            favoriteUtil?.addFavorite("8099", "大世科")
            result = favoriteUtil?.removeFavorite("8099", "大世科") == true
        }
        assertTrue(result)
        assertFalse(AppDatabase.getInstance(appContext)?.FavoriteDao()?.getAll()?.contains(Favorite("8099", "大世科")) == true)
    }

}
