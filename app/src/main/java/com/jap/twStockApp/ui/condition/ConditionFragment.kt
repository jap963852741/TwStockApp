package com.jap.twStockApp.ui.condition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.BuildConfig
import com.jap.twStockApp.databinding.FragmentConditionBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.ui.condition.filter.BiggerOrSmaller
import com.jap.twStockApp.ui.condition.filter.ConditionFilterAdapter
import com.jap.twStockApp.ui.condition.filter.ConditionType
import com.jap.twStockApp.ui.condition.filter.FilterModel
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.ToastUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import javax.inject.Inject

class ConditionFragment : BaseFragment(), View.OnClickListener {

    private lateinit var dashboardBinding: FragmentConditionBinding
    private var conditionViewModel: ConditionViewModel? = null
    private var conditionAdapter: ConditionAdapter? = null
    private var conditionFilterAdapter: ConditionFilterAdapter? = null
    private var favoriteSize: Int = 0

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

        dashboardBinding.conditionSearch.setOnClickListener(this)
        initConditionAdapter()
        dashboardBinding.addFilter.setOnClickListener { addView ->
            if (!BuildConfig.DEBUG) conditionFilterAdapter?.list?.let { if (it.size >= 2) addView.visibility = View.GONE}
            conditionFilterAdapter?.list = conditionFilterAdapter?.list?.plus(FilterModel(ConditionType.Price, BiggerOrSmaller.Bigger, 0.0))
            val size = conditionFilterAdapter?.list?.size ?: return@setOnClickListener
            conditionFilterAdapter?.notifyItemChanged(size - 1)
        }
        conditionViewModel?.text?.observe(viewLifecycleOwner) { conditionAdapter?.submitList(it) }
        conditionViewModel?.favorite?.observe(viewLifecycleOwner) {
            favoriteSize = it.size
            conditionAdapter?.setNewFavoriteList(it.toSet())
        }
        conditionViewModel?.filter?.observe(viewLifecycleOwner) { beginFilter() }
        conditionViewModel?.getFavorite()

        return dashboardBinding.root
    }

    private fun initConditionAdapter() {
        conditionFilterAdapter = ConditionFilterAdapter()
        conditionFilterAdapter?.list = listOf(FilterModel(ConditionType.Price, BiggerOrSmaller.Bigger, 0.0))
        dashboardBinding.recyclerCondition.adapter = conditionFilterAdapter
        conditionFilterAdapter?.notifyItemChanged(0)


        conditionAdapter = ConditionAdapter()
        dashboardBinding.reViewDashboard.adapter = conditionAdapter
        dashboardBinding.reViewDashboard.layoutManager = MyLinearLayoutManager(context)
        conditionAdapter?.rootEvent?.observe(viewLifecycleOwner) { stockNo ->
            FragmentSwitchUtil.getInstance(parentFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
            baseViewModel?.setHomeFragmentSearchText(stockNo)
        }

        conditionAdapter?.favoriteButtonEvent?.observe(viewLifecycleOwner) { stockNoNameFav ->
            if (stockNoNameFav.stockFavorite) {
                if (favoriteSize >= 5) {
                    ToastUtil.shortToast("目前只開放收藏 5 個")
                    return@observe
                }
                conditionViewModel?.addFavorite(stockNoNameFav) { success ->
                    if (success) {
                        conditionAdapter?.updateStatus(stockNoNameFav)
                        favoriteSize++
                    }
                    else ToastUtil.shortToast("add favorite Fail")
                }
            } else {
                conditionViewModel?.removeFavorite(stockNoNameFav) { success ->
                    if (success)  {
                        conditionAdapter?.updateStatus(stockNoNameFav)
                        favoriteSize--
                    }
                    else ToastUtil.shortToast("cancel favorite Fail")
                }
            }
        }
    }

    override fun onClick(v: View?) {
        conditionViewModel?.getFilteredList()
        closeKeyBoard(v)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if(!hidden) conditionViewModel?.getFavorite()
        super.onHiddenChanged(hidden)
    }

    private fun beginFilter() {
        conditionFilterAdapter?.getAllFilterModel()?.let {
            conditionViewModel?.filterList(it)
            conditionViewModel?.updateText()
        }
    }
}
