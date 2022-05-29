package com.jap.twStockApp.ui.condition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twStockApp.Repository.roomdb.Favorite
import com.jap.twStockApp.databinding.ItemDetailBinding
import com.jap.twStockApp.ui.MainActivity
import com.jap.twStockApp.ui.condition.ConditionViewModel.Companion.favorites
import com.jap.twStockApp.ui.home.HomeFragment
import com.jap.twStockApp.util.FavoriteUtil

class ConditonAdapter(
    private val dataList: ArrayList<String?>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() {

    private lateinit var binding: ItemDetailBinding
    var setHomeSearchText: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        val stockno = c!!.split(" ")[0]
        val name = c!!.split(" ")[1]

        holder.tv_dashboard.text = c

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
                favorites.remove(Favorite(stockno, name))
                FavoriteUtil(parentview.context).remove_favorite(stockno, name)
                holder.favorite_button.setIconEnabled(false)
            } else {
                favorites.add(Favorite(stockno, name))
                FavoriteUtil(parentview.context).add_favorite(stockno, name)
                holder.favorite_button.setIconEnabled(true)
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

class VH(binding: ItemDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var tv_dashboard: TextView = binding.tvDashboard
    var favorite_button: SwitchIconView = binding.favoriteButton
}
