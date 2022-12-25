package com.jap.twStockApp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData

fun <T> List<T>?.toArrayList() = ArrayList(this ?: listOf())

fun <T> Fragment.observe(liveData: LiveData<T>?, block: (T) -> Unit) {
    liveData?.observe(viewLifecycleOwner) { block(it) }
}
