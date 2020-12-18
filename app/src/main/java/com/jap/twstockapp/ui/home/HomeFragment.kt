package com.jap.twstockapp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R
import com.jap.twstockapp.dialog.LoadingDialog
import com.jap.twstockapp.roomdb.MyStockUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() , View.OnClickListener{

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var choose_button: Button
    private lateinit var homeAdapter: HomeAdapter
    lateinit var StockNo : String
    lateinit var stocktext: AutoCompleteTextView
//    lateinit var loadingdialog: LoadingDialog
    companion object {
        lateinit var loadingdialog: LoadingDialog
    }
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        loadingdialog =  LoadingDialog(container!!.context,"正在更新...")
        //仅点击外部不可取消
        loadingdialog.setCanceledOnTouchOutside(false)
        //点击返回键和外部都不可取消
        loadingdialog.setCancelable(false)
        val toolbar: Toolbar = root.findViewById(R.id.toolBar_home)
        toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_refresh_black) //把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        val recyclerView: RecyclerView = root.findViewById(R.id.re_view)
        stocktext = root.findViewById(R.id.auto_complete_text)
        StockNo = stocktext.text.toString()

        var vocabulary : ArrayList<String> = arrayListOf("")
        homeViewModel.StockNoArrayList.observe(viewLifecycleOwner, Observer {
            vocabulary = it
            val adapter = ArrayAdapter(container!!.context, android.R.layout.simple_list_item_1, vocabulary)
            auto_complete_text.threshold = 1
            auto_complete_text.setAdapter(adapter)
        })
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            homeAdapter = HomeAdapter(it, container!!)
            recyclerView.setAdapter(homeAdapter)
            recyclerView.setLayoutManager(
                LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            )
        })

        choose_button =root.findViewById<View>(R.id.search) as Button
        choose_button.setOnClickListener(this)


        stocktext.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            /**
             *
             * @param v 被监听的对象
             * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
             * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
             * @return 返回你的动作
             */
            onClick(v)
            event != null && event.keyCode === KeyEvent.KEYCODE_ENTER
        })

//        MyStockUtil(container!!.context).UpdateAllInformation()



        return root
    }

    override fun onClick(v: View?) {
        homeViewModel.update_text(stocktext.text.toString())
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater!!)
        menu.add(Menu.NONE,ActionBar_Menu_Num.update_db,Menu.NONE,R.string.update_db)
    }

    interface ActionBar_Menu_Num {
        companion object {
            const val update_db = 1
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {
                context?.let { MyStockUtil(it).UpdateAllInformation() }
                loadingdialog.show()
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
