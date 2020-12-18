package com.jap.twstockapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R

class HomeAdapter(
    private val dataList: ArrayList<String?>,
    private val parentview: ViewGroup
) :
    RecyclerView.Adapter<VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false),
            parentview
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        holder.tv1.text = c
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}


class VH(itemView: View, parent: View?) :
    RecyclerView.ViewHolder(itemView) {
    var tv1: TextView

    init {
        tv1 = itemView.findViewById(R.id.tv1)
    }
}
