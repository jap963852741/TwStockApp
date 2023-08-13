package com.jap.twStockApp.Repository.network

import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.TwStock
import com.jap.twStockApp.util.SingleStockUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UpdateDataSource {
    private var countSecond = 0

    suspend fun updateDB(loadingPercent: (Int) -> Unit, errorCallBack: (() -> Unit)? = null) = withContext(Dispatchers.IO) {
        val countJob = CoroutineScope(currentCoroutineContext()).launch(Dispatchers.IO) { beginToCount(loadingPercent).cancellable().collect() }
        try {
            val totalInformation: HashMap<String, HashMap<String, String>> = SingleStockUtil.getInstance().Get_HashMap_Num_MapTotalInformation()
            countJob.cancel()

            val db = AppDatabase.getInstance()
            var totoalFinish = 0.0f
            for ((key_number, value_map) in totalInformation) {
                totoalFinish += 1
                loadingPercent.invoke((countSecond + (totoalFinish / totalInformation.size) * (100 - countSecond)).toInt())
                println("$key_number = $value_map")

                var priceToEarningRatio: Double? = null
                if (value_map["PriceToEarningRatio"] != "-") priceToEarningRatio = value_map["PriceToEarningRatio"]?.toDouble()

                var priceBookRatio: Double? = null
                if (value_map["PriceBookRatio"] != "-") priceBookRatio = value_map["PriceBookRatio"]?.toDouble()

                val twStock = TwStock(
                    StockNo = key_number,
                    Name = value_map["Name"],
                    Price = value_map["Price"]?.toDouble(),
                    UpAndDown = value_map["UpAndDown"],
                    UpAndDownPercent = value_map["UpAndDownPercent"],
                    WeekUpAndDownPercent = value_map["WeekUpAndDownPercent"],
                    HighestAndLowestPercent = value_map["HighestAndLowestPercent"],
                    Open = value_map["Open"]?.toDouble(),
                    High = value_map["High"]?.toDouble(),
                    Low = value_map["Low"]?.toDouble(),
                    DealVolume = value_map["DealVolume"]?.replace(",", "")?.toInt(),
                    DealTotalValue = (value_map["DealTotalValue"]?.toDouble()?.times(100000000f))?.toInt(),
                    DividendYield = value_map["DividendYield"]?.toDouble(),
                    PriceToEarningRatio = priceToEarningRatio,
                    PriceBookRatio = priceBookRatio,
                    OperatingRevenue = value_map["OperatingRevenue"]?.toLong(),
                    MoM = value_map["MoM"]?.toDouble(),
                    YoY = value_map["YoY"]?.toDouble(),
                    DirectorsSupervisorsRatio = value_map["DirectorsSupervisorsRatio"]?.toDouble(),
                    ForeignInvestmentRatio = value_map["ForeignInvestmentRatio"]?.toDouble(),
                    InvestmentRation = value_map["InvestmentRation"]?.toDouble(),
                    SelfEmployedRation = value_map["SelfEmployedRation"]?.toDouble(),
                    ThreeBigRation = value_map["ThreeBigRation"]?.toDouble()
                )
                val haveStock: TwStock? = db?.TwStockDao()?.getStockNoInformation(key_number)

                if (haveStock != null) db.TwStockDao().update(twStock)
                else db?.TwStockDao()?.insertAll(twStock)

            }
        } catch (e: Exception) {
            errorCallBack?.invoke()
        }
        countSecond = 0
    }

    private suspend fun beginToCount(loadingPercent: (Int) -> Unit): Flow<Unit> = flow {
        while (currentCoroutineContext().isActive) {
            loadingPercent.invoke(countSecond++)
            delay(1000)
        }
    }
}
