package com.jap.twStockApp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.FragmentHomeBinding
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.ToastUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment(), View.OnClickListener {

    private var loadingDialog: LoadingDialog? = null
    private var homeViewBinding: FragmentHomeBinding? = null
    private val homeViewModel: IHomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewBinding?.toolBarHome?.overflowIcon = resources.getDrawable(R.drawable.ic_refresh_black) // 把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(homeViewBinding?.toolBarHome)
        homeViewBinding?.swipeHome?.setOnRefreshListener {
            homeViewModel.newUpdateStockDb({ loadingDialog?.setProgressBar(it) }, {
                homeViewBinding?.swipeHome?.isRefreshing = false
            })
        }
        setHasOptionsMenu(true)
        loadingDialog = context?.let { LoadingDialog(it, "正在更新...") }.apply {
            this?.setCanceledOnTouchOutside(false)
            this?.setCancelable(false)
        }
        return homeViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        homeViewBinding?.search?.setOnClickListener(this)
    }

    private fun initObserve() {
        homeViewModel.stockNoArrayList.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) return@observe
            val adapter = view?.context?.let { context -> ArrayAdapter(context, android.R.layout.simple_list_item_1, list) }
            homeViewBinding?.autoCompleteText?.setAdapter(adapter)
        }
        homeViewBinding?.autoCompleteText?.setOnEditorActionListener { v, _, event ->
            onClick(v)
            event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
        }
        homeViewBinding?.searchTextHome?.setOnClickListener{
            onClick(it)
        }
        baseViewModel?.homeFragmentSearchText?.observe(viewLifecycleOwner) {
            it?.let {
                homeViewBinding?.autoCompleteText?.setText(it)
                homeViewModel.updateText(it)
            }
        }
        homeViewModel.stockInformation.observe(viewLifecycleOwner) { stockInformation ->
            if (stockInformation.isNullOrEmpty()) {
                ToastUtil.shortToast(getString(R.string.please_input_correct_style))
                return@observe
            }
            homeViewBinding?.reView?.apply {
                adapter = view?.let { HomeAdapter(stockInformation) }
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
        homeViewModel.updateResult.observe(viewLifecycleOwner) {
            if (it.success != null) ToastUtil.shortToast(resources.getString(it.success))
            else if (it.error != null) ToastUtil.shortToast(resources.getString(it.error))
        }
        homeViewModel.loadingDialog.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog?.setProgressBar(0)
                loadingDialog?.show()
            } else loadingDialog?.dismiss()
        }
    }

    override fun onClick(v: View?) {
        homeViewBinding?.searchHint?.isVisible = false
        closeKeyBoard(v)
        homeViewModel.updateText(homeViewBinding?.autoCompleteText?.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateDB -> homeViewModel.newUpdateStockDb({ loadingDialog?.setProgressBar(it) })
        }
        return super.onOptionsItemSelected(item)
    }
}
