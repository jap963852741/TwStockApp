package com.jap.twStockApp.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.FragmentHomeBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), View.OnClickListener {

    val TAG = "HomeFragment"

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog
    private var homeViewBinding: FragmentHomeBinding? = null
    private var fragmentUtil: FragmentSwitchUtil? = null
    private var stockText: AutoCompleteTextView? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as App).createHomeComponent(requireContext())?.inject(this)

        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        fragmentUtil = FragmentSwitchUtil(parentFragmentManager).getInstance()

        fragmentUtil?.manager?.fragments?.run {
            for (fragment in this) {
                if (fragment != null && fragmentUtil?.mStacks?.containsKey(FragmentSwitchUtil.TAB_HOME) == true &&
                    fragmentUtil?.mStacks?.get(FragmentSwitchUtil.TAB_HOME)?.size == 0
                ) {
                    fragmentUtil?.replaceCateFragment(1, fragment)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewBinding?.toolBarHome?.overflowIcon =
            resources.getDrawable(R.drawable.ic_refresh_black) // 把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(homeViewBinding?.toolBarHome)
        setHasOptionsMenu(true)
        stockText = homeViewBinding?.autoCompleteText
        return homeViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel?.stockNoArrayList?.observe(
            viewLifecycleOwner
        ) {
            val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, it)
            auto_complete_text.setAdapter(adapter)
            Log.e("stockNoArrayList", it.toString())
        }
        baseViewModel?.homeFragmentSearchText?.observe(
            viewLifecycleOwner
        ) {
            it?.let {
                stockText?.setText(it)
                homeViewModel?.updateText(it)
            }
        }

        homeViewModel?.stockInformation?.observe(
            viewLifecycleOwner
        ) {
            homeViewBinding?.reView?.apply {
                adapter = HomeAdapter(it, view)
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
        homeViewModel?.updateResult?.observe(
            viewLifecycleOwner
        ) {
            loadingDialog.hide()

            if (it.success != null) {
                toast(resources.getString(it.success))
            } else if (it.error != null) {
                toast(resources.getString(it.error))
            }
        }
        stockText?.setOnEditorActionListener { v, _, event ->
            /**
             *
             * @param v 被监听的对象
             * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
             * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
             * @return 返回你的动作
             */
            onClick(v)
            event != null && event.keyCode === KeyEvent.KEYCODE_ENTER
        }
        homeViewBinding?.search?.setOnClickListener(this)
    }

    private fun toast(s: String) = Toast.makeText(context, s, Toast.LENGTH_LONG).show()

    override fun onClick(v: View?) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v?.windowToken, 0)
        homeViewModel?.updateText(stockText?.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateDB -> {
                homeViewModel?.updateStockDb(loadingDialog)
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
