package com.jap.twStockApp.util

import android.content.Context
import android.content.SharedPreferences

const val APP_OPEN_TIMESTAMP = "APP_OPEN_TIMESTAMP"

fun SharedPreferences.saveString(key: String, value: String) {
    val editor = edit()
    editor.putString(key, value)
    editor.apply()
}

fun SharedPreferences.saveLong(key: String, value: Long) {
    val editor = edit()
    editor.putLong(key, value)
    editor.apply()
}

object SharedPreference {
    var sharedPreferences: SharedPreferences? = null
    private set

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("TwStockApp", Context.MODE_PRIVATE)
    }
}