package com.jap.twStockApp.ui.condition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.FragmentConditionBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.ui.condition.ConditionViewModel.Companion.favorites

class ConditionFragment : BaseFragment(), View.OnClickListener {

    private lateinit var dashboardBinding: FragmentConditionBinding
    private var conditionViewModel: ConditionViewModel? = null
    private var conditionAdapter: ConditonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createConditionComponent().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentConditionBinding.inflate(inflater, container, false)
        conditionViewModel = ViewModelProvider(
            this, ConditionViewModelFactory(application = requireActivity().application)
        )[ConditionViewModel::class.java]

        val condition = arrayOf(
            "現價",
            "漲跌",
            "漲跌現價比",
            "周漲跌現價比",
            "最高最低振福",
            "開盤價",
            "最高價",
            "最低價",
            "交易量",
            "交易總值",
            "殖利率",
            "本益比",
            "股價淨值比",
            "營業收入",
            "月增率",
            "年增率",
            "董監持股比例",
            "外商持股比例",
            "投信持股比例",
            "自營商持股",
            "三大法人持股比例"
        )
        val conditionList: ArrayAdapter<String> = ArrayAdapter(
            container!!.context,
            android.R.layout.simple_spinner_dropdown_item,
            condition
        )
        dashboardBinding.condition1.conditionName.adapter = conditionList
        dashboardBinding.condition2.conditionName.adapter = conditionList
        dashboardBinding.condition3.conditionName.adapter = conditionList
        dashboardBinding.condition4.conditionName.adapter = conditionList
        dashboardBinding.condition5.conditionName.adapter = conditionList

        val symbol = arrayOf(">", "<")
        val symbolList: ArrayAdapter<String> =
            ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, symbol)
        dashboardBinding.condition1.conditionSymbol.adapter = symbolList
        dashboardBinding.condition2.conditionSymbol.adapter = symbolList
        dashboardBinding.condition3.conditionSymbol.adapter = symbolList
        dashboardBinding.condition4.conditionSymbol.adapter = symbolList
        dashboardBinding.condition5.conditionSymbol.adapter = symbolList

        dashboardBinding.conditionSearch.setOnClickListener(this)

        conditionViewModel?.text?.observe(viewLifecycleOwner) {
            conditionAdapter = ConditonAdapter(it, container)
            conditionAdapter?.setHomeSearchText =
                baseViewModel?.let { it::setHomeFragmentSearchText }
            dashboardBinding.reViewDashboard.adapter = conditionAdapter
            dashboardBinding.reViewDashboard.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
        conditionViewModel?.favorite?.observe(viewLifecycleOwner) {
            favorites = it
            dashboardBinding.reViewDashboard.adapter = conditionAdapter
            dashboardBinding.reViewDashboard.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }
        conditionViewModel?.filter?.observe(viewLifecycleOwner) {
            beginFilter()
        }
        conditionViewModel?.get_favorite()

        return dashboardBinding.root
    }

    override fun onResume() {
        super.onResume()
        conditionViewModel?.get_favorite()
    }

    override fun onClick(v: View?) {
        conditionViewModel?.getFilteredList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        conditionViewModel?.get_favorite()
        super.onHiddenChanged(hidden)
    }

    private fun beginFilter() {
        if (dashboardBinding.condition1.textDashboard.text.toString() != "") {
            println("----第一個條件開始篩選-----")
            conditionViewModel?.filter_list(
                dashboardBinding.condition1.conditionName.selectedItem.toString(),
                dashboardBinding.condition1.conditionSymbol.selectedItem.toString(),
                dashboardBinding.condition1.textDashboard.text.toString().toDouble()
            )
        }

        if (dashboardBinding.condition2.textDashboard.text.toString() != "") {
            println("----第二個條件開始篩選-----")
            conditionViewModel?.filter_list(
                dashboardBinding.condition2.conditionName.selectedItem.toString(),
                dashboardBinding.condition2.conditionSymbol.selectedItem.toString(),
                dashboardBinding.condition2.textDashboard.text.toString().toDouble()
            )
        }
        if (dashboardBinding.condition3.textDashboard.text.toString() != "") {
            println("----第三個條件開始篩選-----")
            conditionViewModel?.filter_list(
                dashboardBinding.condition3.conditionName.selectedItem.toString(),
                dashboardBinding.condition3.conditionSymbol.selectedItem.toString(),
                dashboardBinding.condition3.textDashboard.text.toString().toDouble()
            )
        }
        if (dashboardBinding.condition4.textDashboard.text.toString() != "") {
            println("----第四個條件開始篩選-----")
            conditionViewModel?.filter_list(
                dashboardBinding.condition4.conditionName.selectedItem.toString(),
                dashboardBinding.condition4.conditionSymbol.selectedItem.toString(),
                dashboardBinding.condition4.textDashboard.text.toString().toDouble()
            )
        }

        if (dashboardBinding.condition5.textDashboard.text.toString() != "") {
            println("----第五個條件開始篩選-----")
            conditionViewModel?.filter_list(
                dashboardBinding.condition4.conditionName.selectedItem.toString(),
                dashboardBinding.condition4.conditionSymbol.selectedItem.toString(),
                dashboardBinding.condition5.textDashboard.text.toString().toDouble()
            )
        }

        conditionViewModel?.updateText()
    }
}
