package com.jap.twstockapp.ui.home

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R
import com.jap.twstockapp.databinding.FragmentHomeBinding
import com.jap.twstockapp.util.dialog.LoadingDialog
import com.jap.twstockapp.util.FragmentSwitchUtil
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() , View.OnClickListener{

    private lateinit var homeviewbinding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    lateinit var StockNo : String

    companion object {
        lateinit var loadingdialog: LoadingDialog
        lateinit var stocktext: AutoCompleteTextView
        lateinit var homeViewModel: HomeViewModel
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        homeviewbinding = FragmentHomeBinding.inflate(inflater, container, false);
        val toolbar: Toolbar = homeviewbinding.toolBarHome
        val recyclerView: RecyclerView = homeviewbinding.reView

//        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel = ViewModelProvider(this,HomeViewModelFactory(application = requireActivity().application)).get(HomeViewModel::class.java)

        loadingdialog =  LoadingDialog(container!!.context,"正在更新...")//仅点击外部不可取消
        loadingdialog.setCanceledOnTouchOutside(false)//点击返回键和外部都不可取消
        loadingdialog.setCancelable(false)
        toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_refresh_black) //把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        stocktext = homeviewbinding.autoCompleteText
        StockNo = stocktext.text.toString()

        homeViewModel.StockNoArrayList.observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter(container!!.context, android.R.layout.simple_list_item_1, it)
            Log.e("homeViewModel",it.toString())
            auto_complete_text.threshold = 1
            auto_complete_text.setAdapter(adapter)
        })

        homeViewModel.StockInformation.observe(viewLifecycleOwner, Observer {
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

        homeViewModel.updateResult.observe(viewLifecycleOwner, Observer {
            loadingdialog.hide()

            if(it.success != null) {
                Toast.makeText(
                    context,
                    "${it.success} ",
                    Toast.LENGTH_LONG
                ).show()
            }else{
                Toast.makeText(
                    context,
                    "${it.error} ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })


        homeviewbinding.search.setOnClickListener(this)

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

        val fragmentutil = FragmentSwitchUtil(parentFragmentManager).getInstance()

        val fragments = fragmentutil.manager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragmentutil.mStacks!![fragmentutil.TAB_HOME]!!.size == 0) {
                fragmentutil.replaceCateFragment(1,fragment)
            }
        }
        return homeviewbinding.root
    }

    override fun onClick(v: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v?.windowToken, 0)
        homeViewModel.update_text(stocktext.text.toString())
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.home_menu, menu)
       super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateDB -> {
                context.let { homeViewModel.update_stock_db()}
                loadingdialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
