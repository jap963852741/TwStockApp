package com.jap.twStockApp.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

object ToastUtil {
    // It will not OOM if context is a applicationContext
    @SuppressLint("StaticFieldLeak")
    private var instance: Toast? = null
    private var showing: Boolean = false

    fun init(context: Context) {
        instance = Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    fun getInstance(): Toast? {
        return instance
    }

    fun shortToast(string: String) {
        instance?.setText(string)
        instance?.duration = Toast.LENGTH_SHORT
        instance?.show()
    }
}