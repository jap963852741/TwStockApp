package com.jap.twStockApp.ui.condition

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MyLinearLayoutManager(context : Context?) : LinearLayoutManager(
    context,
    RecyclerView.VERTICAL,
    false
) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        }catch (e :IndexOutOfBoundsException) {
            Log.e(this.javaClass.name, e.toString())
        }
    }
}