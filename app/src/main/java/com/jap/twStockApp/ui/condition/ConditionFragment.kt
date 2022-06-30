package com.jap.twStockApp.ui.condition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.databinding.FragmentConditionBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import javax.inject.Inject

class ConditionFragment : BaseFragment(), View.OnClickListener {

    private lateinit var dashboardBinding: FragmentConditionBinding
    private var conditionViewModel: ConditionViewModel? = null
    private var conditionAdapter: ConditionAdapter? = null

    @Inject
    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createConditionComponent(requireContext()).inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentConditionBinding.inflate(inflater, container, false)
        conditionViewModel = ViewModelProvider(this, ConditionViewModelFactory(application = requireActivity().application))[ConditionViewModel::class.java]

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
        val symbolList: ArrayAdapter<String> = ArrayAdapter(container.context, android.R.layout.simple_spinner_dropdown_item, symbol)
        dashboardBinding.condition1.conditionSymbol.adapter = symbolList
        dashboardBinding.condition2.conditionSymbol.adapter = symbolList
        dashboardBinding.condition3.conditionSymbol.adapter = symbolList
        dashboardBinding.condition4.conditionSymbol.adapter = symbolList
        dashboardBinding.condition5.conditionSymbol.adapter = symbolList

        dashboardBinding.conditionSearch.setOnClickListener(this)
        initConditionAdapter()
        conditionViewModel?.text?.observe(viewLifecycleOwner) { conditionAdapter?.submitList(it) }
        conditionViewModel?.favorite?.observe(viewLifecycleOwner) { conditionAdapter?.setNewFavoriteList(it.toSet()) }
        conditionViewModel?.filter?.observe(viewLifecycleOwner) { beginFilter() }
        conditionViewModel?.get_favorite()

        return dashboardBinding.root
    }

    private fun initConditionAdapter() {
        conditionAdapter = ConditionAdapter()
        dashboardBinding.reViewDashboard.adapter = conditionAdapter
        dashboardBinding.reViewDashboard.layoutManager = MyLinearLayoutManager(context)
        conditionAdapter?.rootEvent?.observe(viewLifecycleOwner) { stockNo ->
            FragmentSwitchUtil.getInstance(parentFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
            baseViewModel?.setHomeFragmentSearchText(stockNo)
        }

        conditionAdapter?.favoriteButtonEvent?.observe(viewLifecycleOwner) { stockNoNameFav ->
            if (stockNoNameFav.stockFavorite) {
                conditionViewModel?.addFavorite(stockNoNameFav) { success ->
                    if (success) conditionAdapter?.updateStatus(stockNoNameFav)
                    else Toast.makeText(context, "add favorite Fail", Toast.LENGTH_SHORT).show()
                }
            } else {
                conditionViewModel?.removeFavorite(stockNoNameFav) { success ->
                    if (success) conditionAdapter?.updateStatus(stockNoNameFav)
                    else Toast.makeText(context, "cancel favorite Fail", Toast.LENGTH_SHORT).show()
                }
            }
        }
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
