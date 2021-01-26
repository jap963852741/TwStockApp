package com.jap.twstockapp.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twstockapp.MainActivity
import com.jap.twstockapp.R
import com.jap.twstockapp.databinding.ItemFavoritesBinding
import com.jap.twstockapp.databinding.ItemHomeBinding
import com.jap.twstockapp.roomdb.Favorite
import com.jap.twstockapp.ui.dashboard.DashboardFragment
import com.jap.twstockapp.ui.dashboard.DashboardViewModel
import com.jap.twstockapp.ui.favorites.FavoritesViewModel.Companion.favorites
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.util.FavoriteUtil

class FavoritesAdapter(
    private val dataList: ArrayList<Favorite>,
    private val parentview: ViewGroup
) :

    RecyclerView.Adapter<VH>() {

    private lateinit var binding : ItemFavoritesBinding



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
        for(i in favorites){
            if(stockno == i.StockNo && name  == i.Name){
                favorite_tag = true
                break
            }
        }
        if(favorite_tag){
            holder.favorite_button.setIconEnabled(true)
        }else{
            holder.favorite_button.setIconEnabled(false)
        }


        holder.favorite_button.setOnClickListener {

            if (holder.favorite_button.isIconEnabled) {
                FavoriteUtil(parentview.context).remove_favorite(stockno,name)
                holder.favorite_button.setIconEnabled(false)
            } else {
                FavoriteUtil(parentview.context).add_favorite(stockno,name)
                holder.favorite_button.setIconEnabled(true)
                DashboardFragment.dashboardViewModel.get_favorite()
            }

        }

        holder.itemView.setOnClickListener {
            MainActivity.navigation.setSelectedItemId(MainActivity.navigation.menu.getItem(0).getItemId());
            HomeFragment.stocktext.setText(stockno,false)
            HomeFragment.homeViewModel.update_text(stockno)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}


class VH(binding: ItemFavoritesBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var itemFavorite: TextView = binding.itemFavorites
    var favorite_button : SwitchIconView = binding.favoriteButton2
}
