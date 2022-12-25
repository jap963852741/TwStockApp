package com.jap.twStockApp.ui.favorites

import androidx.recyclerview.widget.DiffUtil
import com.jap.twStockApp.Repository.roomdb.Favorite

class FavoritesDiffUtil: DiffUtil.ItemCallback<Favorite>() {
    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = true // just one viewHolder
    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean = oldItem.StockNo == newItem.StockNo
}