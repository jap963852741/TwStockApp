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
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        AppDatabase::class.java.simpleName
                    ).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            if (INSTANCE?.isOpen == true) {
                INSTANCE?.close()
            }
            INSTANCE = null
        }
    }
}
