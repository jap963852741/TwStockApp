package com.jap.twStockApp.ui.condition

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.databinding.ItemDetailBinding
import com.jap.twStockApp.ui.model.StockNoNameFav
import java.util.*

class ConditionAdapter(var list: List<StockNoNameFav?>) : RecyclerView.Adapter<VH>() {
//    private val diffCallback: DiffCallback = DiffCallback()

    private val _favoriteButtonEvent: MutableLiveData<StockNoNameFav> = MutableLiveData()
    val favoriteButtonEvent: LiveData<StockNoNameFav> = _favoriteButtonEvent

    private val _rootEvent: MutableLiveData<String> = MutableLiveData()
    val rootEvent: LiveData<String> = _rootEvent

    private fun favoriteButtonEvent(stockNoNameFav: StockNoNameFav?) {
        if (stockNoNameFav?.stockNo.isNullOrEmpty() || stockNoNameFav?.stockName.isNullOrEmpty()) return
        stockNoNameFav?.stockFavorite = (stockNoNameFav?.stockFavorite == false)
        _favoriteButtonEvent.postValue(stockNoNameFav)
    }

    private fun rootEvent(stockNoNameFav: StockNoNameFav?) = stockNoNameFav?.stockNo?.let { stockNo -> _rootEvent.postValue(stockNo) }

    fun updateStatus(stockNoNameFav: StockNoNameFav) {
        val newList = list.toMutableList()
        newList.forEachIndexed { index, element ->
            if (stockNoNameFav.stockNo != element?.stockNo) return@forEachIndexed
            newList[index] = stockNoNameFav.copy()
            list = newList
            notifyItemChanged(index)
            return
        }
    }

    fun setNewFavoriteList(favoriteSet: Set<Favorite>) {
        val newList = list.toMutableList()
        newList.forEachIndexed { index, element ->
            if (element != null) {
                val oldFav = newList[index]?.stockFavorite
                if (oldFav == favoriteSet.contains(Favorite(element.stockNo, element.stockName))) return@forEachIndexed
                newList[index] = StockNoNameFav(element.stockNo, element.stockName, oldFav == false)
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false), ::favoriteButtonEvent, ::rootEvent)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.data = list[position]
        val data = holder.data
        holder.tvDashboard.text = "${data?.stockNo} ${data?.stockName}"
        holder.favoriteButton.setIconEnabled(data?.stockFavorite == true, true)
    }

    override fun getItemCount(): Int = list.size

//    fun submitList(newList: List<StockNoNameFav?>) {
//        diffCallback.setList(newList)
//        val result = DiffUtil.calculateDiff(diffCallback)
//        result.dispatchUpdatesTo(this)
//        list = newList
//    }
}

class VH(
    private val binding: ItemDetailBinding,
    private var favoriteButtonEvent: ((StockNoNameFav?) -> Unit),
    private var rootEvent: ((StockNoNameFav?) -> Unit)
) : RecyclerView.ViewHolder(binding.root) {

    var data: StockNoNameFav? = null
    var tvDashboard: TextView = binding.tvDashboard
    var favoriteButton: SwitchIconView = binding.favoriteButton

    init {
        binding.favoriteButton.setOnClickListener { data?.let { favoriteButtonEvent.invoke(it) } }
        binding.root.setOnClickListener { data?.let { rootEvent.invoke(it) } }
    }
}

//class DiffCallback : DiffUtil.Callback() {
//
//    var oldList: List<StockNoNameFav?> = emptyList()
//    var newList: List<StockNoNameFav?> = emptyList()
//
//    fun setList(list: List<StockNoNameFav?>) {
//        oldList = newList
//        newList = list
//    }
//
//    override fun getOldListSize(): Int = oldList.size
//    override fun getNewListSize(): Int = newList.size
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition]?.stockNo == newList[newItemPosition]?.stockNo
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        Log.e("franktest", "getChangePayload ${oldList[oldItemPosition]} ${newList[newItemPosition]} ${oldList[oldItemPosition] == newList[newItemPosition]}")
//        return oldList[oldItemPosition]?.equals(newList[newItemPosition]) == true
//    }
//
//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
////        Log.e("franktest", "getChangePayload ${oldList[oldItemPosition]} ${newList[newItemPosition]} ${oldList[oldItemPosition] == newList[newItemPosition]}")
//        return super.getChangePayload(oldItemPosition, newItemPosition)
//    }
//}
