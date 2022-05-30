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

        fun appDataBaseInit(context: Context?) {
            getInstance(context)
        }

        fun getInstance(context: Context?): AppDatabase? {
            if (INSTANCE != null) return INSTANCE
            if (context == null) return null

            synchronized(AppDatabase::class) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    AppDatabase::class.java.simpleName
                ).build()
            }
            return INSTANCE
        }

        // database" is when your app is being closed by the OS. So let the OS handle it for you.
        // https://stackoverflow.com/questions/6608498/best-place-to-close-database-connection/7739454#7739454
//        fun closeInstance() {
//            if (INSTANCE?.isOpen == true) {
//                INSTANCE?.close()
//            }
//        }
    }
}
