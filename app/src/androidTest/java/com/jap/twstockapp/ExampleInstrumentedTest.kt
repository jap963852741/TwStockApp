package com.jap.twstockapp

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jap.twstockapp.Repository.roomdb.AppDatabase

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jap.twstockapp", appContext.packageName)
        val temp_arraylist = AppDatabase.getInstance(appContext).TwStockDao().getAllStockNo()
        Log.e("StockNoArrayRepository",temp_arraylist.toString())
    }
}