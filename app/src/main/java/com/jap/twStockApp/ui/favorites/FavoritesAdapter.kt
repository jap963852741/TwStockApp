package com.jap.twStockApp.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.databinding.ItemFavoritesBinding
import com.jap.twStockApp.ui.MainActivity
import com.jap.twStockApp.util.FavoriteUtil

class FavoritesAdapter(
    private val dataList: ArrayList<Favorite>,
    private val favoriteUtil: FavoriteUtil
) :

    RecyclerView.Adapter<VH>() {

    private lateinit var binding: ItemFavoritesBinding
    var setHomeSearchText: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val favorite = dataList[position]
        val stockNo = favorite.StockNo
        val name = favorite.Name!!

        holder.itemFavorite.text = "${favorite.StockNo}  ${favorite.Name}"
        holder.favorite_button.setIconEnabled(true)

        holder.favorite_button.setOnClickListener {

            if (holder.favorite_button.isIconEnabled) {
                favoriteUtil.remove_favorite(stockNo, name)
                holder.favorite_button.setIconEnabled(false)
            } else {
                favoriteUtil.add_favorite(stockNo, name)
                holder.favorite_button.setIconEnabled(true)
            }
        }

        holder.itemView.setOnClickListener {
            MainActivity.navigation.selectedItemId = MainActivity.navigation.menu.getItem(0).itemId
            setHomeSearchText?.invoke(stockNo)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}

class VH(binding: ItemFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var itemFavorite: TextView = binding.itemFavorites
    var favorite_button: SwitchIconView = binding.favoriteButton2
}
