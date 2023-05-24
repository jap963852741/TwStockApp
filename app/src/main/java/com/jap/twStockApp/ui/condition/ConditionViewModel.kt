package com.jap.twStockApp.ui.condition

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jap.twStockApp.Repository.AllTwStockTaskFinish
import com.jap.twStockApp.Repository.FavoritesRespository
import com.jap.twStockApp.Repository.GetAllStockRespository
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.Repository.roomdb.TwStock
import com.jap.twStockApp.Repository.roomdb.getParamsByName
import com.jap.twStockApp.ui.condition.filter.BiggerOrSmaller
import com.jap.twStockApp.ui.condition.filter.FilterModel
import com.jap.twStockApp.ui.model.StockNoNameFav
import com.jap.twStockApp.util.FavoriteUtil
import com.jap.twStockApp.util.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ConditionViewModel(app: Application, private val favoritesRespository: FavoritesRespository) : AndroidViewModel(app) {
    private val _text = MutableStateFlow<ArrayList<StockNoNameFav?>>(arrayListOf())
    private val _favorite = MutableLiveData<List<Favorite>>(arrayListOf())
    private val _filter = SingleLiveData<Unit?>()

    val text: Flow<ArrayList<StockNoNameFav?>> = _text
    val favorite: LiveData<List<Favorite>> = _favorite
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
        val operator: ((Number) -> Boolean) = if (filterModel.operator == BiggerOrSmaller.Bigger) {
            { it.toInt() > filterModel.value.toInt() }
        } else {
            { it.toInt() < filterModel.value.toInt() }
        }
        for (twStock in twstocks) {
            val twStockValue = twStock.getParamsByName(filterModel.conditionType.displayName) ?: continue
            if (operator.invoke(twStockValue)) twStocks.add(twStock)
        }
        return twStocks
    }


    fun updateText() {
        viewModelScope.launch(Dispatchers.IO) {
            val tempArray: ArrayList<StockNoNameFav?> = arrayListOf()
            for (twStock in twstocks) {
                if (twStock.Name == null) continue
                tempArray.add(StockNoNameFav(twStock.StockNo, twStock.Name, favorite.value?.toSet()?.contains(Favorite(twStock.StockNo, twStock.Name)) == true))
            }
            _text.emit(tempArray)
        }
    }


    fun getFavorite() = viewModelScope.launch { favoritesRespository.getAllFavorite()?.let { _favorite.value = it } }

    fun addFavorite(stockNoNameFav: StockNoNameFav, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = FavoriteUtil(getApplication<Application>().applicationContext).addFavorite(stockNoNameFav.stockNo, stockNoNameFav.stockName)
        successListener.invoke(result)
    }

    fun removeFavorite(stockNoNameFav: StockNoNameFav, successListener: ((Boolean) -> Unit)) = viewModelScope.launch {
        val result = FavoriteUtil(getApplication<Application>().applicationContext).removeFavorite(stockNoNameFav.stockNo, stockNoNameFav.stockName)
        successListener.invoke(result)
    }

}

