package com.jap.twstockapp.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
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
import com.jap.twstockapp.di.App
import com.jap.twstockapp.util.dialog.LoadingDialog
import com.jap.twstockapp.util.FragmentSwitchUtil
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() , View.OnClickListener{

    val TAG = "HomeFragment"
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    @Inject
    lateinit var loadingDialog: LoadingDialog

    private lateinit var homeviewbinding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var stockNo : String
    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var fragmentUtil : FragmentSwitchUtil

    companion object {
        lateinit var stockText: AutoCompleteTextView
        lateinit var homeViewModel: HomeViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as App).createHomeComponent(requireContext()).inject(this)
        homeViewModel = ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)
        fragmentUtil = FragmentSwitchUtil(parentFragmentManager).getInstance()

        for (fragment in fragmentUtil.manager.fragments) {
            if (fragment != null && fragmentUtil.mStacks!![fragmentUtil.TAB_HOME]!!.size == 0) {
                fragmentUtil.replaceCateFragment(1,fragment)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeviewbinding = FragmentHomeBinding.inflate(inflater, container, false);
        toolbar = homeviewbinding.toolBarHome
        toolbar.overflowIcon = resources.getDrawable(R.drawable.ic_refresh_black) //把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        recyclerView = homeviewbinding.reView
        stockText = homeviewbinding.autoCompleteText
        stockNo = stockText.text.toString()
        return homeviewbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.stockNoArrayList.observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, it)
            Log.e("stockNoArrayList",it.toString())
//            auto_complete_text.threshold = 1
            auto_complete_text.setAdapter(adapter)
        })
        homeViewModel.stockInformation.observe(viewLifecycleOwner, Observer {
            homeAdapter = HomeAdapter(it, view)
            recyclerView.adapter = homeAdapter
            recyclerView.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        })
        homeViewModel.updateResult.observe(viewLifecycleOwner, Observer {
            loadingDialog.hide()

            if(it.success != null) {
                Toast.makeText(
                    context,
                    "${resources.getString(it.success)} ",
                    Toast.LENGTH_LONG
                ).show()
            }else if(it.error != null){
                Toast.makeText(
                    context,
                    "${resources.getString(it.error)} ",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        stockText.setOnEditorActionListener(OnEditorActionListener { v, _, event ->
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
        homeviewbinding.search.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v?.windowToken, 0)
        homeViewModel.updateText(stockText.text.toString())
    }

   override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.home_menu, menu)
       super.onCreateOptionsMenu(menu, inflater)
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateDB -> {
                context.let { homeViewModel.updateStockDb(loadingDialog)}
                loadingDialog.setProgressBar(0)
                loadingDialog.show()
            }
        }
        return super.onOptionsItemSelected(item)
   }

    override fun onPause() {
        super.onPause()
        loadingDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity?.application as App).releaseHomeComponent()
    }

}
