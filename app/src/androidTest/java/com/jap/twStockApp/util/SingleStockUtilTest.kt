package com.jap.twStockApp.util

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
class SingleStockUtilTest {

    private val TAG = this.javaClass.name
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun testInit() {
        SingleStockUtil.init()
        assert(SingleStockUtil.getInstance() != null)
    }

    @Test
    fun testMapFundamental() {
//        val beginTime = System.currentTimeMillis()
//        assert(SingleStockUtil.getInstance().Get_HashMap_Num_MapFundamental().size > 0)
//        Log.i(TAG + this.javaClass.fields, (System.currentTimeMillis() - beginTime).toString())
    }

    @Test
    fun testMapIncome() {
//        val beginTime = System.currentTimeMillis()
//        assert(SingleStockUtil.getInstance().Get_HashMap_Num_MapIncome().size > 0)
//        Log.i(TAG + this.javaClass.fields, (System.currentTimeMillis() - beginTime).toString())
    }
}
