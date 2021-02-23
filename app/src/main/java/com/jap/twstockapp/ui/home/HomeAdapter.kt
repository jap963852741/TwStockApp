package com.jap.twstockapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R
import com.jap.twstockapp.databinding.ItemHomeBinding

class HomeAdapter(
    private val dataList: ArrayList<String>,
    private val parentview: View
) :

    RecyclerView.Adapter<VH>() {
    private lateinit var binding : ItemHomeBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = dataList.get(position)
        holder.itemInformation.text = c
        when {
            position == 0 -> holder.itemInformation.setBackgroundColor(parentview.resources.getColor(R.color.colorLightGray))
            position == 11 -> holder.itemInformation.setBackgroundColor(parentview.resources.getColor(R.color.colorLightGray))
            position == 15 -> holder.itemInformation.setBackgroundColor(parentview.resources.getColor(R.color.colorLightGray))
            position == 19 -> holder.itemInformation.setBackgroundColor(parentview.resources.getColor(R.color.colorLightGray))

            position == 2 -> if (c!!.contains("▲")) holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorRed)) else  holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorLightGreen))
            position == 17 -> if (c!!.contains("-")) holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorLightGreen)) else  holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorRed))
            position == 18 -> if (c!!.contains("-")) holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorLightGreen)) else  holder.itemInformation.setTextColor(parentview.resources.getColor(R.color.colorRed))

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class VH(binding: ItemHomeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var itemInformation: TextView = binding.itemInformation
}
