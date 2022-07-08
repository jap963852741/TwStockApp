package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.Repository.roomdb.TwStock
import com.jap.twStockApp.ui.home.UpdateResult
import com.jap.twStockApp.util.dialog.LoadingDialog
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class StockInformationRepository(val updatedataSource: UpdateDataSource) {

    suspend fun getStockData(applicationContext: Context, StockNo: String): ArrayList<String>? = withContext(Dispatchers.IO) {
        val stockData = AppDatabase.getInstance(applicationContext)?.TwStockDao()?.getStockNoInformation(StockNo)
        if (stockData == null) null
        else getStringArray(stockData)
    }

    private fun getStringArray(stockData: TwStock): ArrayList<String> {
        val tempArray: ArrayList<String> = arrayListOf("公司名稱 : " + stockData.Name)
        tempArray.add("現價 : " + stockData.Price.toString())
        tempArray.add("漲跌 : " + stockData.UpAndDown.toString())
        tempArray.add("漲跌現價比 : " + stockData.UpAndDownPercent.toString())
        tempArray.add("周漲跌現價比 : " + stockData.WeekUpAndDownPercent.toString())
        tempArray.add("最高最低振福 : " + stockData.HighestAndLowestPercent.toString())
        tempArray.add("開盤價 : " + stockData.Open.toString())
        tempArray.add("最高價 : " + stockData.High.toString())
        tempArray.add("最低價 : " + stockData.Low.toString())
        tempArray.add("交易量 : " + stockData.DealVolume.toString())
        tempArray.add("交易總值 : " + stockData.DealTotalValue.toString())
        tempArray.add("--基本面--")
        tempArray.add("殖利率 : " + (stockData.DividendYield ?: "目前無資料"))
        tempArray.add("本益比 : " + (stockData.PriceToEarningRatio ?: "目前無資料"))
        tempArray.add("股價淨值比 : " + (stockData.PriceBookRatio ?: "目前無資料"))
        tempArray.add("--營收成長--")
        tempArray.add("營業收入 : " + (stockData.OperatingRevenue ?: "目前無資料"))
        tempArray.add("月增率 : " + (stockData.MoM ?: "目前無資料"))
        tempArray.add("年增率 : " + (stockData.YoY ?: "目前無資料"))
        tempArray.add("--籌碼--")
        tempArray.add("董監持股比例 : " + (stockData.DirectorsSupervisorsRatio ?: "目前無資料"))
        tempArray.add("外商持股比例 : " + (stockData.ForeignInvestmentRatio ?: "目前無資料"))
        tempArray.add("投信持股比例 : " + (stockData.InvestmentRation ?: "目前無資料"))
        tempArray.add("自營商持股 : " + (stockData.SelfEmployedRation ?: "目前無資料"))
        tempArray.add("三大法人持股比例 : " + (stockData.ThreeBigRation ?: "目前無資料"))
        return tempArray
    }

    suspend fun updateDB(loadingPercent: (Int) -> Unit) = updatedataSource.updateDB(loadingPercent)
}
