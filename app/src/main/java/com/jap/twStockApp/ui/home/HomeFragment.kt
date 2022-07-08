package com.jap.twStockApp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.FragmentHomeBinding
import com.jap.twStockApp.di.App
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil
import com.jap.twStockApp.util.ToastUtil
import com.jap.twStockApp.util.dialog.LoadingDialog
import javax.inject.Inject

class HomeFragment : BaseFragment(), View.OnClickListener {

    val TAG = "HomeFragment"

    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory

    @Inject
    lateinit var loadingDialog: LoadingDialog
    private var homeViewBinding: FragmentHomeBinding? = null
    private var fragmentUtil: FragmentSwitchUtil? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as App).createHomeComponent(requireContext())?.inject(this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        fragmentUtil = FragmentSwitchUtil.getInstance(parentFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewBinding?.toolBarHome?.overflowIcon = resources.getDrawable(R.drawable.ic_refresh_black) // 把三個小點換掉
        (activity as AppCompatActivity?)!!.setSupportActionBar(homeViewBinding?.toolBarHome)
        setHasOptionsMenu(true)
        return homeViewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserve()
        homeViewBinding?.search?.setOnClickListener(this)
    }

    private fun initObserve() {
        homeViewModel?.stockNoArrayList?.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) return@observe
            val adapter = view?.context?.let { context -> ArrayAdapter(context, android.R.layout.simple_list_item_1, list) }
            homeViewBinding?.autoCompleteText?.setAdapter(adapter)
        }
        homeViewBinding?.autoCompleteText?.setOnEditorActionListener { v, _, event ->
            onClick(v)
            event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
        }

        baseViewModel?.homeFragmentSearchText?.observe(viewLifecycleOwner) {
            it?.let {
                homeViewBinding?.autoCompleteText?.setText(it)
                homeViewModel?.updateText(it)
            }
        }
        homeViewModel?.stockInformation?.observe(viewLifecycleOwner) { stockInformation ->
            if (stockInformation.isNullOrEmpty()) return@observe
            homeViewBinding?.reView?.apply {
                adapter = view?.let { HomeAdapter(stockInformation) }
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
        }
        homeViewModel?.updateResult?.observe(viewLifecycleOwner) {
            if (it.success != null) ToastUtil.shortToast(resources.getString(it.success))
            else if (it.error != null) ToastUtil.shortToast(resources.getString(it.error))
        }
        homeViewModel?.loadingDialog?.observe(viewLifecycleOwner) {
            if (it) {
                loadingDialog.setProgressBar(0)
                loadingDialog.show()
            } else loadingDialog.dismiss()
        }
    }

    override fun onClick(v: View?) {
        closeKeyBoard(v)
        homeViewModel?.updateText(homeViewBinding?.autoCompleteText?.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateDB -> homeViewModel?.newUpdateStockDb { loadingDialog.setProgressBar(it) }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity?.application as App).releaseHomeComponent()
    }
}
