package com.jap.twStockApp.ui.condition

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jap.twStockApp.Repository.*
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.TwStock
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class ConditionViewModel(
    app: Application,
    private val favoritesRespository: FavoritesRespository
) : AndroidViewModel(app) {
    val context = getApplication<Application>().applicationContext
    private val _text = MutableLiveData<ArrayList<String?>>().apply {
        value = arrayListOf()
    }
    val _favorite = MutableLiveData<ArrayList<Favorite>>().apply {
        value = arrayListOf()
    }

    val text: LiveData<ArrayList<String?>> = _text
    val favorite: LiveData<ArrayList<Favorite>> = _favorite

    companion object {
        lateinit var twstocks: List<TwStock>
        lateinit var favorites: ArrayList<Favorite>
    }

    fun get_aLL_list() {
        get_favorite()
        GetAllStockRespository().loadInfo(
            context,
            object : AllTwStockTaskFinish {
                override fun onFinish(data: List<TwStock>) {
                    twstocks = data
                    ConditionFragment.mUI_Handler.sendEmptyMessage(ConditionFragment.MSG_TWSTOCK_OK)
                }
            }
        )
    }

    fun filter_list(Name: String, Symbol: String, value: Double) {
        val temp_twstocks: ArrayList<TwStock> = arrayListOf()
        if (Name == "現價" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.Price != null && twstock.Price > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "現價" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.Price != null && twstock.Price < value) {
                    temp_twstocks.add(twstock)
                    System.out.println(twstock)
                }
            }
        }
        if (Name == "漲跌" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.UpAndDown != null && change_string_toint(twstock.UpAndDown) != null && change_string_toint(twstock.UpAndDown)!! > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "漲跌" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.UpAndDown != null && change_string_toint(twstock.UpAndDown) != null && change_string_toint(twstock.UpAndDown)!! < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "漲跌現價比" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.UpAndDownPercent != null && change_string_toint(twstock.UpAndDownPercent) != null && change_string_toint(twstock.UpAndDownPercent)!! > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "漲跌現價比" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.UpAndDownPercent != null && change_string_toint(twstock.UpAndDownPercent) != null && change_string_toint(twstock.UpAndDownPercent)!! < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "周漲跌現價比" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.WeekUpAndDownPercent != null && change_string_toint(twstock.WeekUpAndDownPercent) != null && change_string_toint(twstock.WeekUpAndDownPercent)!! > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "周漲跌現價比" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.WeekUpAndDownPercent != null && change_string_toint(twstock.WeekUpAndDownPercent) != null && change_string_toint(twstock.WeekUpAndDownPercent)!! < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最高最低振福" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.HighestAndLowestPercent != null && change_string_toint(twstock.HighestAndLowestPercent) != null && change_string_toint(twstock.HighestAndLowestPercent)!! < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最高最低振福" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.HighestAndLowestPercent != null && change_string_toint(twstock.HighestAndLowestPercent) != null && change_string_toint(twstock.HighestAndLowestPercent)!! < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "開盤價" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.Open != null && twstock.Open > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "開盤價" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.Open != null && twstock.Open < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最高價" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.High != null && twstock.High > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最高價" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.High != null && twstock.High < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最低價" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.Low != null && twstock.Low > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "最低價" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.Low != null && twstock.Low < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "交易量" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.DealVolume != null && twstock.DealVolume > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "交易量" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.DealVolume != null && twstock.DealVolume < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "交易總值" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.DealTotalValue != null && twstock.DealTotalValue > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "交易總值" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.DealTotalValue != null && twstock.DealTotalValue < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "殖利率" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.DividendYield != null && twstock.DividendYield > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "殖利率" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.DividendYield != null && twstock.DividendYield < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "本益比" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.PriceToEarningRatio != null && twstock.PriceToEarningRatio > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "本益比" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.PriceToEarningRatio != null && twstock.PriceToEarningRatio < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "股價淨值比" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.PriceBookRatio != null && twstock.PriceBookRatio > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "股價淨值比" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.PriceBookRatio != null && twstock.PriceBookRatio < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "營業收入" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.OperatingRevenue != null && twstock.OperatingRevenue > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "營業收入" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.OperatingRevenue != null && twstock.OperatingRevenue < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "月增率" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.MoM != null && twstock.MoM > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "月增率" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.MoM != null && twstock.MoM < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "年增率" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.YoY != null && twstock.YoY > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "年增率" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.YoY != null && twstock.YoY < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "董監持股比例" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.DirectorsSupervisorsRatio != null && twstock.DirectorsSupervisorsRatio > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "董監持股比例" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.DirectorsSupervisorsRatio != null && twstock.DirectorsSupervisorsRatio < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "外商持股比例" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.ForeignInvestmentRatio != null && twstock.ForeignInvestmentRatio > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "外商持股比例" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.ForeignInvestmentRatio != null && twstock.ForeignInvestmentRatio < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "投信持股比例" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.InvestmentRation != null && twstock.InvestmentRation > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "投信持股比例" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.InvestmentRation != null && twstock.InvestmentRation < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "自營商持股" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.SelfEmployedRation != null && twstock.SelfEmployedRation > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "自營商持股" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.SelfEmployedRation != null && twstock.SelfEmployedRation < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "三大法人持股比例" && Symbol == ">") {
            for (twstock in twstocks) {
                if (twstock.ThreeBigRation != null && twstock.ThreeBigRation > value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        if (Name == "三大法人持股比例" && Symbol == "<") {
            for (twstock in twstocks) {
                if (twstock.ThreeBigRation != null && twstock.ThreeBigRation < value) {
                    temp_twstocks.add(twstock)
                }
            }
        }
        twstocks = temp_twstocks
        System.out.println(twstocks)
    }

    fun change_string_toint(s: String): Double? {
        if (s == "--") return null
        var result_string = ""
        result_string = s.replace("▲", "")
            .replace("▼", "")
            .replace("+", "")
        if (result_string.contains("%")) {
            return result_string.replace("%", "").toDouble() * 0.01
        } else {
            return result_string.toDouble()
        }
    }

    fun update_dashboard_livedata() {
        var temp_array: ArrayList<String?>
        if (twstocks.size > 0) {
            temp_array = arrayListOf(twstocks[0].StockNo + " " + twstocks[0].Name)
            for (i in 1..twstocks.size - 1) {
                temp_array.add(twstocks[i].StockNo + " " + twstocks[i].Name)
            }
            _text.postValue(temp_array)
        } else {
            _text.postValue(arrayListOf(""))
        }
    }

    fun get_favorite() {
        val favorite_list = arrayListOf<Favorite>()
        val observer: Observer<List<Favorite>> = object : Observer<List<Favorite>> {
            override fun onNext(item: List<Favorite>) {
                for (fav in item) {
                    favorite_list.add(fav)
                }
            }
            override fun onError(e: Throwable) {
            }
            override fun onComplete() {
                _favorite.value = favorite_list
            }
            override fun onSubscribe(d: Disposable) {
            }
        }

        favoritesRespository.getAllFavorite(context)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
