package com.jap.twStockApp.extensions

import android.annotation.SuppressLint
import android.util.Log

@SuppressLint("LogNotTimber")
fun <T> Any.calculate(block: () -> T): T {
    val startTime = System.currentTimeMillis()
    val data = block.invoke()
    val duration = System.currentTimeMillis() - startTime
    var functionName = ""
    for (stack in Thread.currentThread().stackTrace) {
        if (!stack.methodName.isNullOrEmpty()
            && stack.methodName != "getStackTrace"
            && stack.methodName != "getThreadStackTrace"
            && stack.methodName != "calculate"
        ) {
            functionName = stack.methodName
            break
        }
    }
    Log.i("PTC:" + String.format("%5s", "$duration") + String.format("%20s", Thread.currentThread().name), "${this::class.java.simpleName} : $functionName")
    return data
}

@SuppressLint("LogNotTimber")
fun <T> Any.calculate(addition: String, block: () -> T): T {
    val startTime = System.currentTimeMillis()
    val data = block.invoke()
    val duration = System.currentTimeMillis() - startTime
    var functionName = ""
    for (stack in Thread.currentThread().stackTrace) {
        if (!stack.methodName.isNullOrEmpty() && stack.methodName != "getStackTrace" && stack.methodName != "getThreadStackTrace" && stack.methodName != "calculate") {
            functionName = stack.methodName
            break
        }
    }
    Log.i("PTC:" + String.format("%5s", "$duration") + String.format("%20s", Thread.currentThread().name), "${this::class.java.simpleName} : $addition $functionName")
    return data
}
