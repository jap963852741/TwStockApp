package com.jap.twstockapp.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import com.jap.twstockapp.MainActivity
import com.jap.twstockapp.R

class DashboardAdapter (
    private val dataList: ArrayList<String?>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() , View.OnClickListener{
    lateinit var StockNo : String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false),
            parentview
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        StockNo = c!!
        holder.tv1.text = c
        holder.tv1.setOnClickListener(this)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    @SuppressLint("RestrictedApi")
    override fun onClick(v: View?) {
        (getActivity(parentview.context) as MainActivity).GoHome(StockNo)
    }

}


class VH(itemView: View, parent: View?) :
    RecyclerView.ViewHolder(itemView) {
    var tv1: TextView

    init {
        tv1 = itemView.findViewById(R.id.tv1)
    }
}
