package com.jap.twstockapp.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
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
import com.jap.twstockapp.R
import com.jap.twstockapp.dialog.LoadingDialog


class DashboardFragment : Fragment() , View.OnClickListener{

//    private lateinit var dashboardViewModel: DashboardViewModel


    companion object {
        lateinit var dashboardViewModel: DashboardViewModel
        lateinit var loadingdialog: LoadingDialog
        private lateinit var spinner_name_1 : Spinner
        private lateinit var spinner_symbol_1 : Spinner
        private lateinit var textView: EditText
        private lateinit var search_button: Button
        val MSG_TWSTOCK_OK : Int = 1

        var mUI_Handler: Handler = object : Handler() {
            @SuppressLint("HandlerLeak")
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_TWSTOCK_OK -> {
                        dashboardViewModel.filter_list(spinner_name_1.getSelectedItem().toString(),spinner_symbol_1.getSelectedItem().toString(),textView.text.toString().toDouble())
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
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        spinner_name_1 = root.findViewById(R.id.condition_1_name)
        spinner_symbol_1 = root.findViewById(R.id.condition_1_symbol)
        textView = root.findViewById(R.id.text_dashboard)
        loadingdialog =  LoadingDialog(container!!.context,"正在對比...")

        val lunch =arrayOf<String?>("現價","漲跌","漲跌現價比","周漲跌現價比"
            ,"最高最低振福","開盤價","最高價","最低價","交易量","交易總值","殖利率","本益比"
            ,"股價淨值比","營業收入","月增率","年增率","董監持股比例","外商持股比例","投信持股比例","三大法人持股比例")
        val lunchList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,lunch)
        spinner_name_1.setAdapter(lunchList)

        val symbol =arrayOf<String?>(">","<")
        val symbolList: ArrayAdapter<String?> = ArrayAdapter(container!!.context,android.R.layout.simple_spinner_dropdown_item,symbol)
        spinner_symbol_1.setAdapter(symbolList)




        search_button =root.findViewById<View>(R.id.condition_search) as Button
        search_button.setOnClickListener(this)


        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })





        return root
    }


    override fun onClick(v: View?) {
//        spinner_name_1.getSelectedItem().toString(),spinner_symbol_1.getSelectedItem().toString(),textView.text.toString().toInt()
        dashboardViewModel.get_aLL_list()
        loadingdialog.show()
    }



}


