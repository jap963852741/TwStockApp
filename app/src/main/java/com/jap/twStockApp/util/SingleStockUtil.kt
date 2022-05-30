package com.jap.twStockApp.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.jap.twstockinformation.StockUtil

object SingleStockUtil {

    // It will not OOM if context is a applicationContext
    @SuppressLint("StaticFieldLeak")
    private var instance: StockUtil? = null

    fun init(context: Context) {
        if (context !is Application) throw InitException()
        instance = StockUtil(context)
    }

    fun getInstance(): StockUtil {
        return instance ?: throw InitException()
    }

    private class InitException : Exception()
}
