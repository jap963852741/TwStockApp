package com.jap.twStockApp.util

import android.annotation.SuppressLint
import android.content.Context
import com.jap.twstockinformation.StockUtilV2

object SingleStockUtil {
    // It will not OOM if context is a applicationContext
    @SuppressLint("StaticFieldLeak")
    private var instance: StockUtilV2? = null

    fun init() {
        instance = StockUtilV2()
    }

    fun getInstance(): StockUtilV2 {
        return instance ?: throw InitException()
    }

    private class InitException : Exception()
}
