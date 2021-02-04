package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.TwStock
import com.jap.twstockapp.Repository.roomdb.network.UpdateDataSource
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.ui.home.UpdateResult
import com.jap.twstockinformation.StockUtil
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class StockInformationRepository(val updatedataSource: UpdateDataSource) {
    fun loadInfo(applicationContext : Context,StockNo : String, task: OnTaskFinish){
        Executors.newSingleThreadExecutor().submit {
            val twstock = AppDatabase.getInstance(applicationContext).TwStockDao().getStockNoInformation(StockNo)

            if (twstock != null) {
                var temp_array: ArrayList<String> = arrayListOf("公司名稱 : " + twstock.Name)
                temp_array.add("現價 : "+ twstock.Price.toString())
                temp_array.add("漲跌 : "+ twstock.UpAndDown.toString())
                temp_array.add("漲跌現價比 : "+ twstock.UpAndDownPercent.toString())
                temp_array.add("周漲跌現價比 : "+ twstock.WeekUpAndDownPercent.toString())
                temp_array.add("最高最低振福 : "+ twstock.HighestAndLowestPercent.toString())
                temp_array.add("開盤價 : "+ twstock.Open.toString())
                temp_array.add("最高價 : "+ twstock.High.toString())
                temp_array.add("最低價 : "+ twstock.Low.toString())
                temp_array.add("交易量 : "+ twstock.DealVolume.toString())
                temp_array.add("交易總值 : "+ twstock.DealTotalValue.toString())
                temp_array.add("--基本面--")
                temp_array.add("殖利率 : "+ twstock.DividendYield.toString())
                temp_array.add("本益比 : "+ twstock.PriceToEarningRatio.toString())
                temp_array.add("股價淨值比 : "+ twstock.PriceBookRatio.toString())
                temp_array.add("--營收成長--")
                temp_array.add("營業收入 : "+ twstock.OperatingRevenue.toString())
                temp_array.add("月增率 : "+ twstock.MoM.toString())
                temp_array.add("年增率 : "+ twstock.YoY.toString())
                temp_array.add("--籌碼--")
                temp_array.add("董監持股比例 : "+twstock.DirectorsSupervisorsRatio.toString())
                temp_array.add("外商持股比例 : "+ twstock.ForeignInvestmentRatio.toString())
                temp_array.add("投信持股比例 : "+ twstock.InvestmentRation.toString())
                temp_array.add("自營商持股 : "+ twstock.SelfEmployedRation.toString())
                temp_array.add("三大法人持股比例 : "+ twstock.ThreeBigRation.toString())
                task.onFinish(temp_array)
            }else{
                task.onFinish(arrayListOf(""))
            }
            AppDatabase.destroyInstance()
        }
    }



    fun UpdateAllInformation(context : Context) : Observable<UpdateResult> {
        return updatedataSource.update(context = context)
    }

}
