package com.jap.twStockApp.ui.base

interface RecyclerViewItem {
    fun getSize(): Int
    fun getItem(position: Int): Any
}