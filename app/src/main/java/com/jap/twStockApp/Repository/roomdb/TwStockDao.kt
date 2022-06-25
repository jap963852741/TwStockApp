package com.jap.twStockApp.Repository.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TwStockDao {
    @Query("SELECT * FROM twstock")
    fun getAll(): List<TwStock>

    @Query("SELECT * FROM twstock WHERE StockNo = :Number")
    fun getStockNoInformation(Number: String): TwStock?

    @Query("SELECT * FROM twstock WHERE Price > :Value")
    fun getStockPriceConditionBigger(Value: Int): List<TwStock>
    @Query("SELECT * FROM twstock WHERE Price < :Value")
    fun getStockPriceConditionSmaller(Value: Int): List<TwStock>

    @Query("SELECT (StockNo || ' ' || Name) FROM twstock")
    fun getAllStockNo(): List<String?>?

    @Insert
    fun insertAll(vararg twstock: TwStock)

    @Update
    fun update(data: TwStock)
}
