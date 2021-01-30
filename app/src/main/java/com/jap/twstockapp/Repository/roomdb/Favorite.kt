package com.jap.twstockapp.Repository.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Favorite(
    @PrimaryKey val StockNo: String,
    @ColumnInfo(name = "Name") val Name: String?
)
//{