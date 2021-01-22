package com.jap.twstockapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jap.twstockapp.R
import com.jap.twstockapp.databinding.FragmentFavoritesBinding
import com.jap.twstockapp.databinding.FragmentHomeBinding
import com.jap.twstockapp.ui.home.HomeAdapter
import com.jap.twstockapp.ui.home.HomeFragment
import com.jap.twstockapp.util.FavoriteUtil

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.favorite.observe(viewLifecycleOwner, Observer {
            favoritesAdapter = FavoritesAdapter(it, container!!)
            binding.reViewFavorites.setAdapter(favoritesAdapter)
            binding.reViewFavorites.setLayoutManager(
                LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
            )
        })

        FavoriteUtil(requireContext()).get_all_favorite(favoritesViewModel)

        return binding.getRoot()
    }
}