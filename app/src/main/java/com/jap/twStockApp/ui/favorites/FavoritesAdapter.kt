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
import com.jap.twStockApp.ui.condition.ConditionFragment
import com.jap.twStockApp.ui.favorites.FavoritesViewModel.Companion.favorites
import com.jap.twStockApp.ui.home.HomeFragment
import com.jap.twStockApp.util.FavoriteUtil

class FavoritesAdapter(
    private val dataList: ArrayList<Favorite>,
    private val parentview: ViewGroup
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
        val favorite = dataList.get(position)
        val stockno = favorite.StockNo
        val name = favorite.Name!!

        holder.itemFavorite.text = favorite.StockNo + " " + favorite.Name

        var favorite_tag = false
        for (i in favorites) {
            if (stockno == i.StockNo && name == i.Name) {
                favorite_tag = true
                break
            }
        }
        if (favorite_tag) {
            holder.favorite_button.setIconEnabled(true)
        } else {
            holder.favorite_button.setIconEnabled(false)
        }

        holder.favorite_button.setOnClickListener {

            if (holder.favorite_button.isIconEnabled) {
                FavoriteUtil(parentview.context).remove_favorite(stockno, name)
                holder.favorite_button.setIconEnabled(false)
            } else {
                FavoriteUtil(parentview.context).add_favorite(stockno, name)
                holder.favorite_button.setIconEnabled(true)
                ConditionFragment.conditionViewModel.get_favorite()
            }
        }

        holder.itemView.setOnClickListener {
            MainActivity.navigation.selectedItemId = MainActivity.navigation.menu.getItem(0).itemId
//            HomeFragment.stockText.setText(stockno, false)
            setHomeSearchText?.invoke(stockno)
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
