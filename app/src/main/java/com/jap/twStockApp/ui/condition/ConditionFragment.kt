package com.jap.twStockApp.ui.condition

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.FragmentConditionBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.condition.ConditionViewModel.Companion.favorites

class ConditionFragment : Fragment(), View.OnClickListener {

    lateinit var conditionAdapter: ConditonAdapter

    companion object {
        lateinit var conditionViewModel: ConditionViewModel
        private lateinit var dashboardBinding: FragmentConditionBinding
        private lateinit var search_button: Button
        val MSG_TWSTOCK_OK: Int = 1
        var mUI_Handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {

                when (msg.what) {
                    MSG_TWSTOCK_OK -> {
                        if (dashboardBinding.condition1.textDashboard.text.toString() != null && dashboardBinding.condition1.textDashboard.text.toString() != "") {
                            System.out.println("----第一個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition1.conditionName.selectedItem.toString(),
                                dashboardBinding.condition1.conditionSymbol.selectedItem.toString(),
                                dashboardBinding.condition1.textDashboard.text.toString().toDouble()
                            )
                        }

                        if (dashboardBinding.condition2.textDashboard.text.toString() != null && dashboardBinding.condition2.textDashboard.text.toString() != "") {
                            System.out.println("----第二個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition2.conditionName.selectedItem.toString(),
                                dashboardBinding.condition2.conditionSymbol.selectedItem.toString(),
                                dashboardBinding.condition2.textDashboard.text.toString().toDouble()
                            )
                        }
                        if (dashboardBinding.condition3.textDashboard.text.toString() != null && dashboardBinding.condition3.textDashboard.text.toString() != "") {
                            System.out.println("----第三個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition3.conditionName.selectedItem.toString(),
                                dashboardBinding.condition3.conditionSymbol.selectedItem.toString(),
                                dashboardBinding.condition3.textDashboard.text.toString().toDouble()
                            )
                        }
                        if (dashboardBinding.condition4.textDashboard.text.toString() != null && dashboardBinding.condition4.textDashboard.text.toString() != "") {
                            System.out.println("----第四個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition4.conditionName.selectedItem.toString(),
                                dashboardBinding.condition4.conditionSymbol.selectedItem.toString(),
                                dashboardBinding.condition4.textDashboard.text.toString().toDouble()
                            )
                        }

                        if (dashboardBinding.condition5.textDashboard.text.toString() != null && dashboardBinding.condition5.textDashboard.text.toString() != "") {
                            System.out.println("----第五個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition4.conditionName.selectedItem.toString(),
                                dashboardBinding.condition4.conditionSymbol.selectedItem.toString(),
                                dashboardBinding.condition5.textDashboard.text.toString().toDouble()
                            )
                        }

                        conditionViewModel.update_dashboard_livedata()
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createConditionComponent().inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("ConditionFragment", "onCreateView")
        dashboardBinding = FragmentConditionBinding.inflate(inflater, container, false)
        conditionViewModel = ViewModelProvider(this, ConditionViewModelFactory(application = requireActivity().application)).get(ConditionViewModel::class.java)
//        loadingdialog =  LoadingDialog(container!!.context,"正在對比...")

        val condition = arrayOf<String>("現價", "漲跌", "漲跌現價比", "周漲跌現價比", "最高最低振福", "開盤價", "最高價", "最低價", "交易量", "交易總值", "殖利率", "本益比", "股價淨值比", "營業收入", "月增率", "年增率", "董監持股比例", "外商持股比例", "投信持股比例", "自營商持股", "三大法人持股比例")
        val conditionList: ArrayAdapter<String> = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, condition)
        dashboardBinding.condition1.conditionName.adapter = conditionList
        dashboardBinding.condition2.conditionName.adapter = conditionList
        dashboardBinding.condition3.conditionName.adapter = conditionList
        dashboardBinding.condition4.conditionName.adapter = conditionList
        dashboardBinding.condition5.conditionName.adapter = conditionList

        val symbol = arrayOf<String>(">", "<")
        val symbolList: ArrayAdapter<String> = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_dropdown_item, symbol)
        dashboardBinding.condition1.conditionSymbol.adapter = symbolList
        dashboardBinding.condition2.conditionSymbol.adapter = symbolList
        dashboardBinding.condition3.conditionSymbol.adapter = symbolList
        dashboardBinding.condition4.conditionSymbol.adapter = symbolList
        dashboardBinding.condition5.conditionSymbol.adapter = symbolList

        search_button = dashboardBinding.conditionSearch
        search_button.setOnClickListener(this)

        conditionViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                conditionAdapter = ConditonAdapter(it, container)
                dashboardBinding.reViewDashboard.adapter = conditionAdapter
                dashboardBinding.reViewDashboard.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            }
        )
        conditionViewModel.favorite.observe(
            viewLifecycleOwner,
            Observer {
                favorites = it
                dashboardBinding.reViewDashboard.adapter = conditionAdapter
                dashboardBinding.reViewDashboard.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            }
        )

        conditionViewModel.get_favorite()

        return dashboardBinding.root
    }

    override fun onClick(v: View?) {
        conditionViewModel.get_aLL_list()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        conditionViewModel.get_favorite()
        super.onHiddenChanged(hidden)
    }
}
