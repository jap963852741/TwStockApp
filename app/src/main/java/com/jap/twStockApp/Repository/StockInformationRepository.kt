package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import com.jap.twStockApp.ui.home.UpdateResult
import com.jap.twStockApp.util.dialog.LoadingDialog
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class StockInformationRepository(val updatedataSource: UpdateDataSource) {
    fun loadInfo(applicationContext: Context, StockNo: String, task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            val twstock = AppDatabase.getInstance(applicationContext)?.TwStockDao()
                ?.getStockNoInformation(StockNo)

            if (twstock != null) {
                val tempArray: ArrayList<String> = arrayListOf("公司名稱 : " + twstock.Name)
                tempArray.add("現價 : " + twstock.Price.toString())
                tempArray.add("漲跌 : " + twstock.UpAndDown.toString())
                tempArray.add("漲跌現價比 : " + twstock.UpAndDownPercent.toString())
                tempArray.add("周漲跌現價比 : " + twstock.WeekUpAndDownPercent.toString())
                tempArray.add("最高最低振福 : " + twstock.HighestAndLowestPercent.toString())
                tempArray.add("開盤價 : " + twstock.Open.toString())
                tempArray.add("最高價 : " + twstock.High.toString())
                tempArray.add("最低價 : " + twstock.Low.toString())
                tempArray.add("交易量 : " + twstock.DealVolume.toString())
                tempArray.add("交易總值 : " + twstock.DealTotalValue.toString())
                tempArray.add("--基本面--")
                tempArray.add("殖利率 : " + twstock.DividendYield.toString())
                tempArray.add("本益比 : " + twstock.PriceToEarningRatio.toString())
                tempArray.add("股價淨值比 : " + twstock.PriceBookRatio.toString())
                tempArray.add("--營收成長--")
                tempArray.add("營業收入 : " + twstock.OperatingRevenue.toString())
                tempArray.add("月增率 : " + twstock.MoM.toString())
                tempArray.add("年增率 : " + twstock.YoY.toString())
                tempArray.add("--籌碼--")
                tempArray.add("董監持股比例 : " + twstock.DirectorsSupervisorsRatio.toString())
                tempArray.add("外商持股比例 : " + twstock.ForeignInvestmentRatio.toString())
                tempArray.add("投信持股比例 : " + twstock.InvestmentRation.toString())
                tempArray.add("自營商持股 : " + twstock.SelfEmployedRation.toString())
                tempArray.add("三大法人持股比例 : " + twstock.ThreeBigRation.toString())
                task.onFinish(tempArray)
            } else {
                task.onFinish(arrayListOf(""))
            }
        }
    }

    fun updateAllInformation(
        loadingDialog: LoadingDialog
    ): Observable<UpdateResult> {
        return updatedataSource.update(loadingDialog = loadingDialog)
    }
}
