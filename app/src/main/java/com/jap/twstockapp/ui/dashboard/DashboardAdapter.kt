package com.jap.twstockapp.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.zagum.switchicon.SwitchIconView
import com.jap.twstockapp.MainActivity
import com.jap.twstockapp.databinding.ItemDetailBinding
import com.jap.twstockapp.ui.dashboard.DashboardFragment.Companion.dashboardViewModel
import com.jap.twstockapp.ui.dashboard.DashboardViewModel.Companion.favorites
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.util.FavoriteUtil


class DashboardAdapter(
    private val dataList: ArrayList<String?>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() {


    private lateinit var binding : ItemDetailBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

//        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
//        val holder = VH(v,parentview)
        binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)

//        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        val stockno = c!!.split(" ")[0]
        val name = c!!.split(" ")[1]

        holder.tv_dashboard.text = c

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
                dashboardViewModel.get_favorite()
            }

        }
        holder.itemView.setOnClickListener {
            var StockNo = dataList.get(holder.adapterPosition)!!.split(" ")[0]//.substring(0,4)
            MainActivity.navigation.setSelectedItemId(MainActivity.navigation.menu.getItem(0).getItemId());
            HomeFragment.stocktext.setText(StockNo,false)
            HomeFragment.homeViewModel.update_text(StockNo)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}


class VH(binding: ItemDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var tv_dashboard: TextView = binding.tvDashboard
    var favorite_button : SwitchIconView = binding.favoriteButton
}
