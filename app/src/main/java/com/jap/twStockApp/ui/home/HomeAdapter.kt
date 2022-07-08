package com.jap.twStockApp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.ItemHomeBinding

class HomeAdapter(private val dataList: ArrayList<String>) : RecyclerView.Adapter<VH>() {
    private lateinit var binding: ItemHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList[position]
        holder.itemInformation.text = c
        when (position) {
            0 -> holder.itemInformation.setBackgroundColor(holder.colorLightGray)
            11 -> holder.itemInformation.setBackgroundColor(holder.colorLightGray)
            15 -> holder.itemInformation.setBackgroundColor(holder.colorLightGray)
            19 -> holder.itemInformation.setBackgroundColor(holder.colorLightGray)
            else -> holder.itemInformation.setBackgroundColor(holder.colorWhite)
        }
        when (position) {
            2 -> if (c.contains("▲")) holder.itemInformation.setTextColor(holder.colorRed) else holder.itemInformation.setTextColor(holder.colorLightGreen)
            17 -> if (c.contains("-")) holder.itemInformation.setTextColor(holder.colorLightGreen) else holder.itemInformation.setTextColor(holder.colorRed)
            18 -> if (c.contains("-")) holder.itemInformation.setTextColor(holder.colorLightGreen) else holder.itemInformation.setTextColor(holder.colorRed)
            21 -> holder.itemInformation.setTextColor(holder.colorBlack)
        }
    }

    override fun getItemCount(): Int = dataList.size
}

class VH(binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
    val colorLightGray = itemView.resources.getColor(R.color.colorLightGray)
    val colorRed = itemView.resources.getColor(R.color.colorRed)
    val colorLightGreen = itemView.resources.getColor(R.color.colorLightGreen)
    val colorBlack = itemView.resources.getColor(R.color.colorBlack)
    val colorWhite = itemView.resources.getColor(R.color.colorWhite)

    var itemInformation: TextView = binding.itemInformation
}
