package com.jap.twstockapp.ui.condition

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.databinding.FragmentConditionBinding
import com.jap.twstockapp.util.dialog.LoadingDialog
import com.jap.twstockapp.ui.condition.ConditionViewModel.Companion.favorites
import com.jap.twstockapp.ui.favorites.FavoriteViewModelFactory
import com.jap.twstockapp.ui.favorites.FavoritesViewModel


class ConditionFragment : Fragment() , View.OnClickListener{


    companion object {
        lateinit var conditionViewModel: ConditionViewModel
        private lateinit var conditionAdapter: ConditonAdapter
        lateinit var loadingdialog: LoadingDialog
        private lateinit var dashboardBinding: FragmentConditionBinding
        private lateinit var search_button: Button
        val MSG_TWSTOCK_OK : Int = 1
        var mUI_Handler: Handler = object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_TWSTOCK_OK -> {
                        if( dashboardBinding.textDashboard.text.toString() != null && dashboardBinding.textDashboard.text.toString() != "") {
                            System.out.println("----第一個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition1Name.getSelectedItem().toString(),
                                dashboardBinding.condition1Symbol.getSelectedItem().toString(),
                                dashboardBinding.textDashboard.text.toString().toDouble()
                            )
                        }

                        if( dashboardBinding.textDashboard2.text.toString() != null && dashboardBinding.textDashboard2.text.toString() != ""){
                            System.out.println("----第二個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition2Name.getSelectedItem().toString(),
                                dashboardBinding.condition2Symbol.getSelectedItem().toString(),
                                dashboardBinding.textDashboard2.text.toString().toDouble())
                        }
                        if( dashboardBinding.textDashboard3.text.toString() != null && dashboardBinding.textDashboard3.text.toString() != ""){
                            System.out.println("----第三個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition3Name.getSelectedItem().toString(),
                                dashboardBinding.condition3Symbol.getSelectedItem().toString(),
                                dashboardBinding.textDashboard3.text.toString().toDouble())
                        }
                        if( dashboardBinding.textDashboard4.text.toString() != null && dashboardBinding.textDashboard4.text.toString() != ""){
                            System.out.println("----第四個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition4Name.getSelectedItem().toString(),
                                dashboardBinding.condition4Symbol.getSelectedItem().toString(),
                                dashboardBinding.textDashboard4.text.toString().toDouble())}

                        if( dashboardBinding.textDashboard5.text.toString() != null && dashboardBinding.textDashboard5.text.toString() != ""){
                            System.out.println("----第五個條件開始篩選-----")
                            conditionViewModel.filter_list(
                                dashboardBinding.condition5Name.getSelectedItem().toString(),
                                dashboardBinding.condition5Symbol.getSelectedItem().toString(),
                                dashboardBinding.textDashboard5.text.toString().toDouble())}

                        conditionViewModel.update_dashboard_livedata()
                    }
                }
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.e("ConditionFragment","onCreateView")
        conditionViewModel = ViewModelProvider(this,
            ConditionViewModelFactory(application = requireActivity().application)
        ).get(ConditionViewModel::class.java)
        dashboardBinding = FragmentConditionBinding.inflate(inflater, container, false)

        loadingdialog =  LoadingDialog(container!!.context,"正在對比...")

        val condition =arrayOf<String?>("現價","漲跌","漲跌現價比","周漲跌現價比"
            ,"最高最低振福","開盤價","最高價","最低價","交易量","交易總值","殖利率","本益比"
            ,"股價淨值比","營業收入","月增率","年增率","董監持股比例","外商持股比例","投信持股比例","自營商持股","三大法人持股比例")
        val conditionList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,condition)
        dashboardBinding.condition1Name.setAdapter(conditionList)
        dashboardBinding.condition2Name.setAdapter(conditionList)
        dashboardBinding.condition3Name.setAdapter(conditionList)
        dashboardBinding.condition4Name.setAdapter(conditionList)
        dashboardBinding.condition5Name.setAdapter(conditionList)

        val symbol =arrayOf<String?>(">","<")
        val symbolList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,symbol)
        dashboardBinding.condition1Symbol.setAdapter(symbolList)
        dashboardBinding.condition2Symbol.setAdapter(symbolList)
        dashboardBinding.condition3Symbol.setAdapter(symbolList)
        dashboardBinding.condition4Symbol.setAdapter(symbolList)
        dashboardBinding.condition5Symbol.setAdapter(symbolList)

        search_button =dashboardBinding.conditionSearch
        search_button.setOnClickListener(this)


       conditionViewModel.text.observe(viewLifecycleOwner, Observer {
           conditionAdapter = ConditonAdapter(it, container!!)
           dashboardBinding.reViewDashboard.setAdapter(conditionAdapter)
           dashboardBinding.reViewDashboard.setLayoutManager(
                LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            )
        })
        conditionViewModel.favorite.observe(viewLifecycleOwner, Observer {
            favorites = it
            dashboardBinding.reViewDashboard.setAdapter(conditionAdapter)
            dashboardBinding.reViewDashboard.setLayoutManager(
                LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            )
        })

        conditionViewModel.get_favorite()

        return dashboardBinding.root
    }

    override fun onClick(v: View?) {
        conditionViewModel.get_aLL_list()
        loadingdialog.show()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        conditionViewModel.get_favorite()
        super.onHiddenChanged(hidden)
    }
}


