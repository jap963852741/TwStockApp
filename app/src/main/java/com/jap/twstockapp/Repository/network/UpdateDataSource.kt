package com.jap.twstockapp.Repository.network

import android.content.Context
import com.jap.twstockapp.R
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import com.jap.twstockapp.Repository.roomdb.TwStock
import com.jap.twstockapp.ui.home.UpdateResult
import com.jap.twstockapp.util.dialog.LoadingDialog
import com.jap.twstockinformation.StockUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class UpdateDataSource {
    lateinit var mDisposable: Disposable

    fun update(context: Context , loadingDialog:LoadingDialog): Observable<UpdateResult> {

        return Observable.create {

            var countSecond = 0
            interval(1000,object : RxAction {
                override fun action(number: Long) {
                    loadingDialog.setProgressBar(countSecond)
                    countSecond += 1
                }
            })

            val totalInformation:HashMap<String,HashMap<String,String>> = StockUtil(context).Get_HashMap_Num_MapTotalInformation()

            cancel()

            val db = AppDatabase.getInstance(context)
            try {
                var totoalFinish = 0.0f
                for ((key_number, value_map) in totalInformation) {
                    totoalFinish += 1
                    loadingDialog.setProgressBar(countSecond + (totoalFinish/totalInformation.size)*(100 - countSecond))

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
                it.onNext(UpdateResult(success = R.string.update_success))
            }catch (e: Exception){
                it.onError(e)
            }
            AppDatabase.destroyInstance()
        }
    }
    /**
     * 每隔milliseconds毫秒后执行指定动作
     * @param milliSeconds
     * @param rxAction
     */
    private fun interval(milliSeconds : Long,  rxAction :RxAction) {
        Observable.interval(milliSeconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Long> {
                override fun onComplete() {
                    TODO("Not yet implemented")
                }
                override fun onSubscribe(d: Disposable) {
                    mDisposable = d
                }
                override fun onNext(t: Long) {
                    rxAction.action(t)
                }
                override fun onError(e: Throwable?) {
                    TODO("Not yet implemented")
                }
            });
    }

    interface RxAction {
        fun action( number:Long)
    }

    /**
     * 停止數秒loading
     */
    private fun cancel() {
        if (!mDisposable.isDisposed) {
            mDisposable.dispose();
        }
    }

}