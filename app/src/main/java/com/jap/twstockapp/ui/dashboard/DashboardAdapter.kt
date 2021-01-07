package com.jap.twstockapp.ui.dashboard

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jap.twstockapp.MainActivity
import com.jap.twstockapp.R
import com.jap.twstockapp.ui.home.HomeFragment


class DashboardAdapter (
    private val dataList: ArrayList<String?>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        val holder = VH(v,parentview)

        holder.itemView.setOnClickListener {
            var StockNo = dataList.get(holder.adapterPosition)!!.substring(0,4)
            MainActivity.navigation.setSelectedItemId(MainActivity.navigation.menu.getItem(0).getItemId());
            HomeFragment.stocktext.setText(StockNo,false)
            Log.i("DashboardAdapter", "position = " + holder.adapterPosition)
            HomeFragment.homeViewModel.update_text(StockNo)

        }

        return holder

//        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false),parentview)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        holder.tv1.text = c
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


//    @SuppressLint("RestrictedApi")
//    override fun onClick(v: View?) {
////        (getActivity(parentview.context) as MainActivity).Condition_ToHome(StockNo)
//        MainActivity.fragmentutil.selectedTab(MainActivity.fragmentutil.TAB_HOME)
//        Log.i("onClick",StockNo)
//        HomeFragment.stocktext.setText(StockNo)
//    }

}


class VH(itemView: View, parent: View?) :
    RecyclerView.ViewHolder(itemView) {
    var tv1: TextView

    init {
        tv1 = itemView.findViewById(R.id.tv1)
    }
}
