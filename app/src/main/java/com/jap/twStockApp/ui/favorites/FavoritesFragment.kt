package com.jap.twStockApp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.FragmentFavoritesBinding
import com.jap.twStockApp.extensions.observe
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FragmentSwitchUtil

class FavoritesFragment : BaseFragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoritesViewModel = ViewModelProvider(this, FavoriteViewModelFactory(application = requireActivity().application))[FavoritesViewModel::class.java]

        initAdapter()
        favoritesViewModel.favorite.observe(viewLifecycleOwner) {
            favoritesAdapter.dataList = it
            favoritesAdapter.notifyDataSetChanged()
        }

        getFavoriteData()
        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) getFavoriteData()
        super.onHiddenChanged(hidden)
    }

    private fun getFavoriteData() = favoritesViewModel.getFavoriteNew()

    private fun initAdapter() {
        favoritesAdapter = FavoritesAdapter()
        observe(favoritesAdapter.favoriteButtonEvent) { favorite ->
            favoritesViewModel.removeFavorite(favorite) { success ->
                if (success) favoritesAdapter.removeFavorite(favorite)
                else Toast.makeText(context, "cancel favorite Fail", Toast.LENGTH_SHORT).show()
            }
        }
        observe(favoritesAdapter.stockNoEvent) { stockNo ->
            FragmentSwitchUtil.getInstance(parentFragmentManager)?.selectedTab(FragmentSwitchUtil.TAB_HOME)
            baseViewModel?.setHomeFragmentSearchText(stockNo)
        }
        binding.reViewFavorites.adapter = favoritesAdapter
        binding.reViewFavorites.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL,
            false
        )
    }
}
