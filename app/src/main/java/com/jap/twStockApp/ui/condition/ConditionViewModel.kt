package com.jap.twStockApp.ui.condition

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.*
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.TwStock
import com.jap.twStockApp.Repository.roomdb.getParamsByName
import com.jap.twStockApp.ui.condition.filter.BiggerOrSmaller
import com.jap.twStockApp.ui.condition.filter.FilterModel
import com.jap.twStockApp.ui.model.StockNoNameFav
import com.jap.twStockApp.util.FavoriteUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class ConditionViewModel(app: Application, private val favoritesRespository: FavoritesRespository) : AndroidViewModel(app) {
    private val _text = MutableLiveData<ArrayList<StockNoNameFav?>>(arrayListOf())
    private val _favorite = MutableLiveData<ArrayList<Favorite>>(arrayListOf())
    private val _filter = MutableLiveData<Unit>()

    val text: LiveData<ArrayList<StockNoNameFav?>> = _text
    val favorite: LiveData<ArrayList<Favorite>> = _favorite
    val filter: LiveData<Unit?> = _filter

    private lateinit var twstocks: List<TwStock>

    fun getFilteredList() {
        getFavorite()
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

    fun filterList(filterModels: List<FilterModel>) {
        filterModels.forEach {
            if (it.value.isNaN()) return@forEach
            twstocks = filterAConditionInTwStock(it)
        }
    }

    private fun filterAConditionInTwStock(filterModel: FilterModel): ArrayList<TwStock> {
        val twStocks: ArrayList<TwStock> = arrayListOf()
        if (filterModel.operator == BiggerOrSmaller.Bigger) {
            for (twStock in twstocks) {
                val twStockValue = twStock.getParamsByName(filterModel.conditionType.displayName)
                if (twStockValue != null && twStockValue.toInt() > filterModel.value.toInt()) {
                    twStocks.add(twStock)
                }
            }
        } else if (filterModel.operator == BiggerOrSmaller.Smaller) {
            for (twStock in twstocks) {
                val twStockValue = twStock.getParamsByName(filterModel.conditionType.displayName)
                if (twStockValue != null && twStockValue.toInt() < filterModel.value.toInt()) {
                    twStocks.add(twStock)
                }
            }
        }
        return twStocks
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

    fun getFavorite() {
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
