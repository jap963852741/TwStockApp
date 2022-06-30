package com.jap.twStockApp.ui.condition.filter

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.ItemConditionBinding

class ConditionFilterAdapter : RecyclerView.Adapter<VH>() {
    var list: List<FilterModel>? = listOf()

    private val _conditionTextEvent: MutableLiveData<String> = MutableLiveData()
    val conditionTextEvent: LiveData<String> = _conditionTextEvent

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemConditionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        list?.let { list ->
            val data = list[position]
            holder.data = data
            holder.conditionName.adapter = holder.conditionAdapter
            holder.conditionSymbol.adapter = holder.symbolAdapter
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    fun getAllFilterModel(): List<FilterModel>? {
        return list
    }
}

class VH(
    private val binding: ItemConditionBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.conditionName.setIndexChangeListener { index -> data?.conditionType = conditionTypeArray[index] }
        binding.conditionSymbol.setIndexChangeListener { index -> data?.operator = biggerOrSmallerArray[index] }
        binding.conditionText.doOnTextChanged { text, _, _, _ ->
            try {
                data?.value = text.toString().toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(binding.root.context, "請輸入數字", Toast.LENGTH_SHORT).show()
            }
        }
    }

    var data: FilterModel? = null
    val conditionAdapter = ArrayAdapter(binding.root.context, R.layout.simple_spinner_dropdown_item, getConditionTypeNameArray())
    val symbolAdapter = ArrayAdapter(binding.root.context, R.layout.simple_spinner_dropdown_item, getBiggerOrSmallerNameArray())
    var conditionName = binding.conditionName
    var conditionSymbol = binding.conditionSymbol
    var conditionText = binding.conditionText

    private fun getConditionTypeNameArray(): Array<String> = arrayOf(
        ConditionType.Price.displayName,
        ConditionType.UpAndDown.displayName,
        ConditionType.UpAndDownPercent.displayName,
        ConditionType.WeekUpAndDownPercent.displayName,
        ConditionType.HighestAndLowestPercent.displayName,
        ConditionType.Open.displayName,
        ConditionType.High.displayName,
        ConditionType.Low.displayName,
        ConditionType.DealVolume.displayName,
        ConditionType.DealTotalValue.displayName,
        ConditionType.DividendYield.displayName,
        ConditionType.PriceToEarningRatio.displayName,
        ConditionType.PriceBookRatio.displayName,
        ConditionType.OperatingRevenue.displayName,
        ConditionType.MoM.displayName,
        ConditionType.YoY.displayName,
        ConditionType.DirectorsSupervisorsRatio.displayName,
        ConditionType.ForeignInvestmentRatio.displayName,
        ConditionType.InvestmentRation.displayName,
        ConditionType.SelfEmployedRation.displayName,
        ConditionType.ThreeBigRatio.displayName
    )

    val conditionTypeArray: Array<ConditionType> = arrayOf(
        ConditionType.Price,
        ConditionType.UpAndDown,
        ConditionType.UpAndDownPercent,
        ConditionType.WeekUpAndDownPercent,
        ConditionType.HighestAndLowestPercent,
        ConditionType.Open,
        ConditionType.High,
        ConditionType.Low,
        ConditionType.DealVolume,
        ConditionType.DealTotalValue,
        ConditionType.DividendYield,
        ConditionType.PriceToEarningRatio,
        ConditionType.PriceBookRatio,
        ConditionType.OperatingRevenue,
        ConditionType.MoM,
        ConditionType.YoY,
        ConditionType.DirectorsSupervisorsRatio,
        ConditionType.ForeignInvestmentRatio,
        ConditionType.InvestmentRation,
        ConditionType.SelfEmployedRation,
        ConditionType.ThreeBigRatio
    )

    private fun getBiggerOrSmallerNameArray(): Array<String> = arrayOf(
        BiggerOrSmaller.Bigger.displayName,
        BiggerOrSmaller.Smaller.displayName
    )

    val biggerOrSmallerArray: Array<BiggerOrSmaller> = arrayOf(
        BiggerOrSmaller.Bigger,
        BiggerOrSmaller.Smaller
    )
}

