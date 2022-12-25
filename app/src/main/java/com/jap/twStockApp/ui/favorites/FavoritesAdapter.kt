package com.jap.twStockApp.ui.favorites

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.databinding.ItemFavoritesBinding
import com.jap.twStockApp.extensions.toArrayList
import com.jap.twStockApp.ui.MainActivity
import com.jap.twStockApp.ui.model.StockNoNameFav
import com.jap.twStockApp.util.FavoriteUtil
import com.jap.twStockApp.util.SingleStockUtil.init
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritesAdapter : ListAdapter<Favorite, VH>(FavoritesDiffUtil()) {
    private val _favoriteButtonEvent: MutableLiveData<Favorite> = MutableLiveData()
    val favoriteButtonEvent: LiveData<Favorite> = _favoriteButtonEvent
    private val _stockNoEvent: MutableLiveData<String> = MutableLiveData()
    val stockNoEvent: LiveData<String> = _stockNoEvent

    fun removeFavorite(favorite: Favorite) = currentList.forEachIndexed { index, element ->
        if (element != favorite) return@forEachIndexed
        val newList = currentList.toMutableList()
        newList.removeAt(index)
        submitList(newList)
        return
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false), { _favoriteButtonEvent.postValue(it) }, { _stockNoEvent.postValue(it) })

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val favorite = currentList[position]
        holder.data = favorite
        holder.itemFavorite.text = "${favorite?.StockNo}  ${favorite?.Name}"
        holder.favoriteButton.setIconEnabled(true)
    }

    override fun getItemCount(): Int = currentList.size
}

class VH(binding: ItemFavoritesBinding, private var favoriteButtonEvent: ((Favorite?) -> Unit), private var stockNoEvent: ((String?) -> Unit)) : RecyclerView.ViewHolder(binding.root) {
    var data: Favorite? = null
    var itemFavorite: TextView = binding.itemFavorites
    var favoriteButton: SwitchIconView = binding.favoriteButton

    init {
        binding.favoriteButton.setOnClickListener { data?.let { favoriteButtonEvent.invoke(it) } }
        binding.itemFavorites.setOnClickListener { data?.let { stockNoEvent.invoke(it.StockNo) } }
    }
}
