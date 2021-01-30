package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.TwStock
import java.util.concurrent.Executors

class GetAllStockRespository {
    fun loadInfo(applicationContext : Context, task: AllTwStockTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val all = AppDatabase.getInstance(applicationContext).TwStockDao().getAll()

            task.onFinish(all)
        }
    }
}
interface AllTwStockTaskFinish{
    fun onFinish(data: List<TwStock>)
}