package com.jap.twStockApp.Repository.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TwStock::class, Favorite::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun FavoriteDao(): FavoriteDao
    abstract fun TwStockDao(): TwStockDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun appDataBaseInit(context: Context) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase::class.java.simpleName).build()
        }

        fun getInstance(): AppDatabase? {
            return INSTANCE
        }

    }
}
