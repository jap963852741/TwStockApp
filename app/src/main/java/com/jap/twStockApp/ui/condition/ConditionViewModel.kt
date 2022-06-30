package com.jap.twStockApp.ui.condition

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.*
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.TwStock
import com.jap.twStockApp.ui.model.StockNoNameFav
import com.jap.twStockApp.util.FavoriteUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class ConditionViewModel(
    app: Application,
    private val favoritesRespository: FavoritesRespository
) : AndroidViewModel(app) {
    private val _text = MutableLiveData<ArrayList<StockNoNameFav?>>(arrayListOf())
    private val _favorite = MutableLiveData<ArrayList<Favorite>>(arrayListOf())
    private val _filter = MutableLiveData<Unit>()

    val text: LiveData<ArrayList<StockNoNameFav?>> = _text
    val favorite: LiveData<ArrayList<Favorite>> = _favorite
    val filter: LiveData<Unit?> = _filter

    private lateinit var twstocks: List<TwStock>

    fun getFilteredList() {
        get_favorite()
        GetAllStockRespository().loadInfo(
            getApplication<Application>().applicationContext,
            object : AllTwStockTaskFinish {
                override fun onFinish(data: List<TwStock>) {
                    twstocks = data
                    _filter.postValue(null)
                }
            }
        )
    }

    fun filter_list(Name: String, Symbol: String, value: Double) {
        twstocks = filterAConditionInTwStock(Name, value, Symbol)
        println(twstocks)
    }

    private fun filterAConditionInTwStock(name: String, input: Number, symbol: String): ArrayList<TwStock> {
        val twStocks: ArrayList<TwStock> = arrayListOf()

        if (symbol == "<") {
            for (twStock in twstocks) {
                val twStockValue = twStock.getParamsByName(name)
                if (twStockValue != null && twStockValue.toInt() < input.toInt()) {
                    twStocks.add(twStock)
                }
            }
        } else {
            for (twStock in twstocks) {
                val twStockValue = twStock.getParamsByName(name)
                if (twStockValue != null && twStockValue.toInt() > input.toInt()) {
                    twStocks.add(twStock)
                }
            }
        }

        return twStocks
    }

    private fun TwStock.getParamsByName(name: String): Number? {
        when (name) {
            "現價" -> return Price
            "漲跌" -> return stringToDouble(UpAndDown)
            "漲跌現價比" -> return stringToDouble(UpAndDownPercent)
            "周漲跌現價比" -> return stringToDouble(WeekUpAndDownPercent)
            "最高最低振福" -> return stringToDouble(HighestAndLowestPercent)
            "開盤價" -> return Open
            "最高價" -> return High
            "最低價" -> return Low
            "交易量" -> return DealVolume
            "交易總值" -> return DealTotalValue
            "殖利率" -> return DividendYield
            "本益比" -> return PriceToEarningRatio
            "股價淨值比" -> return PriceBookRatio
            "營業收入" -> return OperatingRevenue
            "月增率" -> return MoM
            "年增率" -> return YoY
            "董監持股比例" -> return DirectorsSupervisorsRatio
            "外商持股比例" -> return ForeignInvestmentRatio
            "投信持股比例" -> return InvestmentRation
            "自營商持股" -> return SelfEmployedRation
            "三大法人持股比例" -> return ThreeBigRation
        }
        return null
    }

    private fun stringToDouble(s: String?): Double? {
        if (s == null) return null
        if (s == "--") return null
        val resultString = s.replace("▲", "")
            .replace("▼", "")
            .replace("+", "")

        return if (resultString.contains("%")) {
            resultString.replace("%", "").toDouble() * 0.01
        } else {
            resultString.toDouble()
        }
    }

    fun updateText() {
        val tempArray: ArrayList<StockNoNameFav?> = arrayListOf()
        if (twstocks.isEmpty()) _text.postValue(arrayListOf())
        for (twStock in twstocks) {
            if (twStock.Name == null) continue
            tempArray.add(StockNoNameFav(twStock.StockNo, twStock.Name, favorite.value?.toSet()?.contains(Favorite(twStock.StockNo, twStock.Name)) == true))
        }
        _text.postValue(tempArray)
    }

    fun get_favorite() {
        val favorites = arrayListOf<Favorite>()
        val observer: Observer<List<Favorite>> = object : Observer<List<Favorite>> {
            override fun onNext(item: List<Favorite>) {
                item.forEach { favorites.add(it) }
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                _favorite.value = favorites
            }

            override fun onSubscribe(d: Disposable) {
            }
        }

        favoritesRespository.getAllFavorite()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun addFavorite(stockNoNameFav: StockNoNameFav, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = FavoriteUtil(getApplication<Application>().applicationContext).addFavorite(stockNoNameFav.stockNo, stockNoNameFav.stockName)
        successListener.invoke(result)
    }

    fun removeFavorite(stockNoNameFav: StockNoNameFav, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = FavoriteUtil(getApplication<Application>().applicationContext).removeFavorite(stockNoNameFav.stockNo, stockNoNameFav.stockName)
        successListener.invoke(result)
    }
}
