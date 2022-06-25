package com.jap.twStockApp.extensions

fun <T> List<T>?.toArrayList() = ArrayList(this ?: listOf())
