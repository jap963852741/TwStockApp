package com.jap.twstockapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jap.twstockapp.R
import com.jap.twstockapp.databinding.FragmentFavoritesBinding
import com.jap.twstockapp.databinding.FragmentHomeBinding

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        val textView: TextView = binding.textNotifications
        favoritesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return binding.getRoot()
    }
}