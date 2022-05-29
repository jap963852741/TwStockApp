package com.jap.twstockapp.Repository.roomdb

import androidx.room.*

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Insert
    fun insertAll(vararg favorite: Favorite)

    @Update
    fun update(data: Favorite)

    @Delete
    fun delete(data: Favorite)
}
