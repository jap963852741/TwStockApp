package com.jap.twStockApp.ui.condition

import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.LayoutInflater
import android.view.MotionEvent.*
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.BuildConfig
import com.jap.twStockApp.databinding.FragmentConditionBinding
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.ui.condition.filter.BiggerOrSmaller
import com.jap.twStockApp.ui.condition.filter.ConditionFilterAdapter
import com.jap.twStockApp.ui.condition.filter.ConditionType
import com.jap.twStockApp.ui.condition.filter.FilterModel
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.ToastUtil
import com.jap.twStockApp.util.dialog.LoadingDialog


class ConditionFragment : BaseFragment(), View.OnClickListener {
    private lateinit var dashboardBinding: FragmentConditionBinding
    private var conditionViewModel: ConditionViewModel? = null
    private var conditionAdapter: ConditionAdapter? = null
    private var conditionFilterAdapter: ConditionFilterAdapter? = null
    private var favoriteSize: Int = 0
    private var loadingDialog: LoadingDialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dashboardBinding = FragmentConditionBinding.inflate(inflater, container, false)
        conditionViewModel = ViewModelProvider(this, ConditionViewModelFactory(application = requireActivity().application))[ConditionViewModel::class.java]
        loadingDialog = context?.let { LoadingDialog(it, "正在更新...") }.apply {
            this?.setCanceledOnTouchOutside(false)
            this?.setCancelable(false)
        }

        dashboardBinding.conditionSearch.setOnClickListener(this)
        initConditionAdapter()
        dashboardBinding.addFilter.setOnClickListener { addView ->
            if (!BuildConfig.DEBUG) conditionFilterAdapter?.list?.let { if (it.size >= 2) addView.visibility = View.GONE }
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

    @SuppressLint("ClickableViewAccessibility")
    private fun initConditionAdapter() {
        conditionFilterAdapter = ConditionFilterAdapter()
        conditionFilterAdapter?.list = listOf(FilterModel(ConditionType.Price, BiggerOrSmaller.Bigger, 0.0))
        dashboardBinding.recyclerCondition.adapter = conditionFilterAdapter
        conditionFilterAdapter?.notifyItemChanged(0)


        conditionAdapter = ConditionAdapter()

        var downEventY: Float? = null
        val display: Display? = activity?.windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val maxY: Int = size.y
        // not to listen click
        dashboardBinding.reViewDashboard.setOnTouchListener { v, event ->
            when (event.action) {
                ACTION_UP -> {
                    downEventY = null
                    if (dashboardBinding.searchComponent.isVisible) {
                        if (dashboardBinding.searchComponent.scaleY > 0.9) {
                            dashboardBinding.searchComponent.scaleY = 1f
                        } else {
                            dashboardBinding.searchComponent.isVisible = false
                        }
                    }
                }
                ACTION_MOVE -> {
                    if (downEventY == null) {
                        downEventY = event.rawY
                    }
                    dashboardBinding.searchComponent.pivotY = 0f
                    val moveInterval = event.rawY - (downEventY ?: 0F)
                    if (moveInterval > 10) { // scale bigger
                        if (!dashboardBinding.searchComponent.isVisible) {
                            if (moveInterval > 50) dashboardBinding.searchComponent.isVisible = true
                        } else {
                            dashboardBinding.searchComponent.scaleY = 1f
                        }
                    } else { // scale smaller
                        if (!dashboardBinding.searchComponent.isVisible) return@setOnTouchListener false
                        val scalePercent = ((downEventY ?: event.rawY) + moveInterval) / (downEventY ?: event.rawY)
                        if (scalePercent >= 0) dashboardBinding.searchComponent.scaleY = scalePercent
                        else dashboardBinding.searchComponent.isVisible = false
                    }
                }
            }
            false
        }
        dashboardBinding.reViewDashboard.adapter = conditionAdapter
        dashboardBinding.reViewDashboard.layoutManager = MyLinearLayoutManager(context)
        conditionAdapter?.rootEvent?.observe(viewLifecycleOwner) { stockNo ->
            activity?.let { FragmentSwitchUtil.getInstance(it)?.selectedTab(FragmentSwitchUtil.TAB_HOME) }
            baseViewModel?.setHomeFragmentSearchText(stockNo)
        }

        conditionAdapter?.favoriteButtonEvent?.observe(viewLifecycleOwner) { stockNoNameFav ->
            if (stockNoNameFav.stockFavorite) {
                if (favoriteSize >= 30) {
                    ToastUtil.shortToast("目前只開放收藏 30 個")
                    return@observe
                }
                conditionViewModel?.addFavorite(stockNoNameFav) { success ->
                    if (success) {
                        conditionAdapter?.updateStatus(stockNoNameFav)
                        favoriteSize++
                    } else ToastUtil.shortToast("add favorite Fail")
                }
            } else {
                conditionViewModel?.removeFavorite(stockNoNameFav) { success ->
                    if (success) {
                        conditionAdapter?.updateStatus(stockNoNameFav)
                        favoriteSize--
                    } else ToastUtil.shortToast("cancel favorite Fail")
                }
            }
        }
    }

    override fun onClick(v: View?) {
        conditionViewModel?.getFilteredList()
        closeKeyBoard(v)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) conditionViewModel?.getFavorite()
        super.onHiddenChanged(hidden)
    }

    private fun beginFilter() {
        conditionFilterAdapter?.getAllFilterModel()?.let {
            conditionViewModel?.filterList(it)
            conditionViewModel?.updateText()
        }
    }
}
