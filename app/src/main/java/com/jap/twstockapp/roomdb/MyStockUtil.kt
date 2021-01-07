package com.jap.twstockapp.roomdb

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.dashboard.DashboardViewModel
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockinformation.MainActivity
import com.jap.twstockinformation.StockUtil

class MyStockUtil(applicationContext : Context){
    val st = StockUtil(applicationContext)
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()

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
                    val PriceBookRatio = value_map.get("PriceBookRatio")?.toDouble()
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
                db.close()
                HomeFragment.loadingdialog.dismiss();
        }.start()

    fun getAdapter(vocabulary: ArrayList<String>?){
        Thread{
            val temp_arraylist = db.TwStockDao().getAllStockNo()
            System.out.println(vocabulary)
            for (i in 0..temp_arraylist!!.size-1) {
//                System.out.println(temp_arraylist?.get(i))
                vocabulary?.add(temp_arraylist?.get(i).toString())
            }
            db.close()
        }.start()

    }

    fun get_stock_information(result: MutableLiveData<ArrayList<String?>>, StockNo : String){
        Thread{
            val twstock = db.TwStockDao().getStockNoInformation(StockNo)
            if (twstock != null) {
                var temp_array: ArrayList<String?> = arrayListOf("公司名稱 : " + twstock.Name)
                AddInfoToAarray("現價 : ", twstock.Price.toString(), temp_array)
                AddInfoToAarray("漲跌 : ", twstock.UpAndDown.toString(), temp_array)
                AddInfoToAarray("漲跌現價比 : ", twstock.UpAndDownPercent.toString(), temp_array)
                AddInfoToAarray("周漲跌現價比 : ", twstock.WeekUpAndDownPercent.toString(), temp_array)
                AddInfoToAarray("最高最低振福 : ", twstock.HighestAndLowestPercent.toString(), temp_array)
                AddInfoToAarray("開盤價 : ", twstock.Open.toString(), temp_array)
                AddInfoToAarray("最高價 : ", twstock.High.toString(), temp_array)
                AddInfoToAarray("最低價 : ", twstock.Low.toString(), temp_array)
                AddInfoToAarray("交易量 : ", twstock.DealVolume.toString(), temp_array)
                AddInfoToAarray("交易總值 : ", twstock.DealTotalValue.toString(), temp_array)
                AddInfoToAarray("--", "基本面--", temp_array)
                AddInfoToAarray("殖利率 : ", twstock.DividendYield.toString(), temp_array)
                AddInfoToAarray("本益比 : ", twstock.PriceToEarningRatio.toString(), temp_array)
                AddInfoToAarray("股價淨值比 : ", twstock.PriceBookRatio.toString(), temp_array)
                AddInfoToAarray("--", "營收成長--", temp_array)
                AddInfoToAarray("營業收入 : ", twstock.OperatingRevenue.toString(), temp_array)
                AddInfoToAarray("月增率 : ", twstock.MoM.toString(), temp_array)
                AddInfoToAarray("年增率 : ", twstock.YoY.toString(), temp_array)
                AddInfoToAarray("--", "籌碼--", temp_array)
                AddInfoToAarray("董監持股比例 : ",twstock.DirectorsSupervisorsRatio.toString(),temp_array)
                AddInfoToAarray("外商持股比例 : ", twstock.ForeignInvestmentRatio.toString(), temp_array)
                AddInfoToAarray("投信持股比例 : ", twstock.InvestmentRation.toString(), temp_array)
                AddInfoToAarray("自營商持股 : ", twstock.SelfEmployedRation.toString(), temp_array)
                AddInfoToAarray("三大法人持股比例 : ", twstock.ThreeBigRation.toString(), temp_array)
                result.postValue(temp_array)
            }else{
                result.postValue(arrayListOf(""))
            }
            db.close()

        }.start()
    }
    fun AddInfoToAarray(s : String? , s2 :String? ,temp_array: ArrayList<String?>?){
        if (s2 != null){
            temp_array?.add(s + s2)
        }
    }

    fun get_all_twstock(){
        Thread {
            DashboardViewModel.twstocks = db.TwStockDao().getAll()
            db.close()
            DashboardFragment.loadingdialog.dismiss()
            DashboardFragment.mUI_Handler.sendEmptyMessage(DashboardFragment.MSG_TWSTOCK_OK)
        }.start()
    }



}