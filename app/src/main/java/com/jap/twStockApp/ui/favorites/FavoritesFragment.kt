package com.jap.twStockApp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.FragmentFavoritesBinding
import com.jap.twStockApp.ui.base.BaseFragment
import com.jap.twStockApp.util.FavoriteUtil

class FavoritesFragment : BaseFragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        favoritesViewModel = ViewModelProvider(
            this,
            FavoriteViewModelFactory(application = requireActivity().application)
        )[FavoritesViewModel::class.java]

        favoritesViewModel.favorite.observe(viewLifecycleOwner) {
            favoritesAdapter = FavoritesAdapter(it, FavoriteUtil(activity?.application))
            favoritesAdapter.setHomeSearchText =
                baseViewModel?.let { it::setHomeFragmentSearchText }
            binding.reViewFavorites.adapter = favoritesAdapter
            binding.reViewFavorites.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        }

        favoritesViewModel.get_favorite()

        return binding.root
    }
}
