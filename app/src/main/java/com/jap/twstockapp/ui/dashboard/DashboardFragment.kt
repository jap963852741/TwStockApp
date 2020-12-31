package com.jap.twstockapp.ui.dashboard

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
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R
import com.jap.twstockapp.dialog.LoadingDialog
import com.jap.twstockapp.ui.home.HomeAdapter


class DashboardFragment : Fragment() , View.OnClickListener{

//    private lateinit var dashboardViewModel: DashboardViewModel


    companion object {
        lateinit var dashboardViewModel: DashboardViewModel
        private lateinit var dashboardAdapter: DashboardAdapter
        lateinit var loadingdialog: LoadingDialog
        private lateinit var spinner_name_1 : Spinner
        private lateinit var spinner_name_2 : Spinner
        private lateinit var spinner_name_3 : Spinner
        private lateinit var spinner_name_4 : Spinner
        private lateinit var spinner_name_5 : Spinner

        private lateinit var spinner_symbol_1 : Spinner
        private lateinit var spinner_symbol_2 : Spinner
        private lateinit var spinner_symbol_3 : Spinner
        private lateinit var spinner_symbol_4 : Spinner
        private lateinit var spinner_symbol_5 : Spinner

        private lateinit var textView: EditText
        private lateinit var textView_2: EditText
        private lateinit var textView_3: EditText
        private lateinit var textView_4: EditText
        private lateinit var textView_5: EditText

        private lateinit var search_button: Button
        val MSG_TWSTOCK_OK : Int = 1

        var mUI_Handler: Handler = object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_TWSTOCK_OK -> {
                        if( textView.text.toString() != null && textView.text.toString() != "") {
                            System.out.println("----第一個條件開始篩選-----")
                            dashboardViewModel.filter_list(
                                spinner_name_1.getSelectedItem().toString(),
                                spinner_symbol_1.getSelectedItem().toString(),
                                textView.text.toString().toDouble()
                            )
                        }
                        if( textView_2.text.toString() != null && textView_2.text.toString() != ""){
                            System.out.println("----第二個條件開始篩選-----")
                            dashboardViewModel.filter_list(spinner_name_2.getSelectedItem().toString(),spinner_symbol_2.getSelectedItem().toString(),textView_2.text.toString().toDouble())
                        }
                        if( textView_3.text.toString() != null && textView_3.text.toString() != ""){
                            System.out.println("----第三個條件開始篩選-----")
                            dashboardViewModel.filter_list(spinner_name_3.getSelectedItem().toString(),spinner_symbol_3.getSelectedItem().toString(),textView_3.text.toString().toDouble())
                        }
                        if( textView_4.text.toString() != null && textView_4.text.toString() != ""){
                            System.out.println("----第四個條件開始篩選-----")
                            dashboardViewModel.filter_list(spinner_name_4.getSelectedItem().toString(),spinner_symbol_4.getSelectedItem().toString(),textView_4.text.toString().toDouble())}

                        if( textView_5.text.toString() != null && textView_5.text.toString() != ""){
                            System.out.println("----第五個條件開始篩選-----")
                            dashboardViewModel.filter_list(spinner_name_5.getSelectedItem().toString(),spinner_symbol_5.getSelectedItem().toString(),textView_5.text.toString().toDouble())}

                        dashboardViewModel.update_dashboard_livedata()
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
        Log.i("DashboardFragment","onCreateView savedInstanceState"+savedInstanceState.toString())

        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        spinner_name_1 = root.findViewById(R.id.condition_1_name)
        spinner_name_2 = root.findViewById(R.id.condition_2_name)
        spinner_name_3 = root.findViewById(R.id.condition_3_name)
        spinner_name_4 = root.findViewById(R.id.condition_4_name)
        spinner_name_5 = root.findViewById(R.id.condition_5_name)

        spinner_symbol_1 = root.findViewById(R.id.condition_1_symbol)
        spinner_symbol_2 = root.findViewById(R.id.condition_2_symbol)
        spinner_symbol_3 = root.findViewById(R.id.condition_3_symbol)
        spinner_symbol_4 = root.findViewById(R.id.condition_4_symbol)
        spinner_symbol_5 = root.findViewById(R.id.condition_5_symbol)

        textView = root.findViewById(R.id.text_dashboard)
        textView_2 = root.findViewById(R.id.text_dashboard_2)
        textView_3 = root.findViewById(R.id.text_dashboard_3)
        textView_4 = root.findViewById(R.id.text_dashboard_4)
        textView_5 = root.findViewById(R.id.text_dashboard_5)

        loadingdialog =  LoadingDialog(container!!.context,"正在對比...")

        val condition =arrayOf<String?>("現價","漲跌","漲跌現價比","周漲跌現價比"
            ,"最高最低振福","開盤價","最高價","最低價","交易量","交易總值","殖利率","本益比"
            ,"股價淨值比","營業收入","月增率","年增率","董監持股比例","外商持股比例","投信持股比例","自營商持股","三大法人持股比例")
        val conditionList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,condition)
        spinner_name_1.setAdapter(conditionList)
        spinner_name_2.setAdapter(conditionList)
        spinner_name_3.setAdapter(conditionList)
        spinner_name_4.setAdapter(conditionList)
        spinner_name_5.setAdapter(conditionList)

        val symbol =arrayOf<String?>(">","<")
        val symbolList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,symbol)
        spinner_symbol_1.setAdapter(symbolList)
        spinner_symbol_2.setAdapter(symbolList)
        spinner_symbol_3.setAdapter(symbolList)
        spinner_symbol_4.setAdapter(symbolList)
        spinner_symbol_5.setAdapter(symbolList)


        search_button =root.findViewById<View>(R.id.condition_search) as Button
        search_button.setOnClickListener(this)

        val recyclerView: RecyclerView = root.findViewById(R.id.re_view_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            dashboardAdapter = DashboardAdapter(it, container!!)
            recyclerView.setAdapter(dashboardAdapter)
            recyclerView.setLayoutManager(
                LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            )
        })





        return root
    }


    override fun onClick(v: View?) {
//        spinner_name_1.getSelectedItem().toString(),spinner_symbol_1.getSelectedItem().toString(),textView.text.toString().toInt()
        dashboardViewModel.get_aLL_list()
        loadingdialog.show()
    }



}


