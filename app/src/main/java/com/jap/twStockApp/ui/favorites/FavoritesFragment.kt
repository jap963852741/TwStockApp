package com.jap.twStockApp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twStockApp.databinding.FragmentFavoritesBinding
import com.jap.twStockApp.ui.favorites.FavoritesViewModel.Companion.favorites

class FavoritesFragment : Fragment() {

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
        ).get(FavoritesViewModel::class.java)
        favoritesViewModel.favorite.observe(
            viewLifecycleOwner,
            Observer {
                favorites = it
                favoritesAdapter = FavoritesAdapter(it, container!!)
                binding.reViewFavorites.setAdapter(favoritesAdapter)
                binding.reViewFavorites.setLayoutManager(
                    LinearLayoutManager(
                        context,
                        RecyclerView.VERTICAL,
                        false
                    )
                )
                binding.reViewFavorites.adapter = favoritesAdapter
            }
        )

        favoritesViewModel.get_favorite()

        return binding.getRoot()
    }
}
