package com.jap.twStockApp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jap.twstockapp", appContext.packageName)
//        val tempArraylist = AppDatabase.getInstance(appContext)?.TwStockDao()?.getAllStockNo()
    }

    @Test
    fun haveStock() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jap.twstockapp", appContext.packageName)
        val tempArraylist = AppDatabase.getInstance(appContext)?.TwStockDao()?.getAllStockNo()
        assert(tempArraylist != null)
    }
}
