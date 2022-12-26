package com.jap.twStockApp.Repository

import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.extensions.toArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StockNoArrayRepository {
    suspend fun newLoadInfo(): ArrayList<String> = withContext(Dispatchers.IO) {
        AppDatabase.getInstance()?.TwStockDao()?.getAllStockNo()?.filter { !it.isNullOrEmpty() }.toArrayList()
    }
}
