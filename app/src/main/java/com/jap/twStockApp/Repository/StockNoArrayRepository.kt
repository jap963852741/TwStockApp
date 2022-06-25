package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.extensions.toArrayList
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class StockNoArrayRepository {
    suspend fun newLoadInfo(applicationContext: Context): ArrayList<String> = withContext(Dispatchers.IO) {
        AppDatabase.getInstance(applicationContext)?.TwStockDao()?.getAllStockNo()?.filter { !it.isNullOrEmpty() }.toArrayList()
    }
}
