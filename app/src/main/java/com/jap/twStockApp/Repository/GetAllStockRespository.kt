package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.TwStock
import java.util.concurrent.Executors

class GetAllStockRespository {
    fun loadInfo(applicationContext: Context, task: AllTwStockTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val all = AppDatabase.getInstance(applicationContext).TwStockDao().getAll()

            task.onFinish(all)
        }
    }
}
interface AllTwStockTaskFinish {
    fun onFinish(data: List<TwStock>)
}
