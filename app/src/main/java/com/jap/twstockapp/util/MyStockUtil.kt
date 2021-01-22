package com.jap.twstockapp.util

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.jap.twstockapp.roomdb.AppDatabase
import com.jap.twstockapp.roomdb.AppDatabase.Companion.destroyInstance
import com.jap.twstockapp.roomdb.TwStock
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.dashboard.DashboardViewModel
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockinformation.StockUtil

class MyStockUtil(applicationContext : Context){
    val st = StockUtil(applicationContext)
    val db = AppDatabase.getInstance(applicationContext)

    fun UpdateAllInformation() = Thread {
            val TotalInformation:HashMap<String,HashMap<String,String>> = st.Get_HashMap_Num_MapTotalInformation()

            for ((key_number, value_map) in TotalInformation) {
                println("$key_number = $value_map")
                var Name = value_map.get("Name")
                var Price: Double? = null
                if (value_map.containsKey("Price")) {
                    Price = value_map.get("Price")?.toDouble()
                }
                var UpAndDown = value_map.get("UpAndDown")
                var UpAndDownPercent = value_map.get("UpAndDownPercent")
                var WeekUpAndDownPercent = value_map.get("WeekUpAndDownPercent")
                var HighestAndLowestPercent = value_map.get("HighestAndLowestPercent")
                var Open = value_map.get("Open")?.toDouble()
                var High = value_map.get("High")?.toDouble()
                var Low = value_map.get("Low")?.toDouble()
                var DealVolume = value_map.get("DealVolume")?.replace(",", "")?.toInt()

                var DealTotalValue: Int? = null
                if (value_map.containsKey("DealTotalValue")) {
                    DealTotalValue =
                        (value_map.get("DealTotalValue")?.toDouble()!! * 100000000f).toInt()
                }

                var DividendYield = value_map.get("DividendYield")?.toDouble()

                var PriceToEarningRatio: Double? = null
                if (value_map.get("PriceToEarningRatio") != "-") {
                    PriceToEarningRatio = value_map.get("PriceToEarningRatio")?.toDouble()
                }

                var PriceBookRatio: Double? = null
                if (value_map.get("PriceBookRatio") != "-") {
                    PriceBookRatio = value_map.get("PriceBookRatio")?.toDouble()
                }


                val OperatingRevenue = value_map.get("OperatingRevenue")?.toLong()
                val MoM = value_map.get("MoM")?.toDouble()
                val YoY = value_map.get("YoY")?.toDouble()
                val DirectorsSupervisorsRatio =
                    value_map.get("DirectorsSupervisorsRatio")?.toDouble()
                val ForeignInvestmentRatio = value_map.get("ForeignInvestmentRatio")?.toDouble()
                val InvestmentRation = value_map.get("InvestmentRation")?.toDouble()
                val SelfEmployedRation = value_map.get("SelfEmployedRation")?.toDouble()
                val ThreeBigRation = value_map.get("ThreeBigRation")?.toDouble()


                val twStock = TwStock(
                    StockNo = key_number,
                    Name = Name,
                    Price = Price,
                    UpAndDown = UpAndDown,
                    UpAndDownPercent = UpAndDownPercent,
                    WeekUpAndDownPercent = WeekUpAndDownPercent,
                    HighestAndLowestPercent = HighestAndLowestPercent,
                    Open = Open,
                    High = High,
                    Low = Low,
                    DealVolume = DealVolume,
                    DealTotalValue = DealTotalValue,
                    DividendYield = DividendYield,
                    PriceToEarningRatio = PriceToEarningRatio,
                    PriceBookRatio = PriceBookRatio,
                    OperatingRevenue = OperatingRevenue,
                    MoM = MoM,
                    YoY = YoY,
                    DirectorsSupervisorsRatio = DirectorsSupervisorsRatio,
                    ForeignInvestmentRatio = ForeignInvestmentRatio,
                    InvestmentRation = InvestmentRation,
                    SelfEmployedRation = SelfEmployedRation,
                    ThreeBigRation = ThreeBigRation
                )
                val HaveStock: TwStock? = db.TwStockDao().getStockNoInformation(key_number)
                if (HaveStock != null) {
                    db.TwStockDao().update(twStock)
                } else {
                    db.TwStockDao().insertAll(twStock)

                }
            }
                destroyInstance()
                HomeFragment.loadingdialog.dismiss();
        }.start()



    fun get_all_twstock(){
        Thread {
            DashboardViewModel.twstocks = db.TwStockDao().getAll()
            destroyInstance()
            DashboardFragment.loadingdialog.dismiss()
            DashboardFragment.mUI_Handler.sendEmptyMessage(DashboardFragment.MSG_TWSTOCK_OK)
        }.start()
    }

}