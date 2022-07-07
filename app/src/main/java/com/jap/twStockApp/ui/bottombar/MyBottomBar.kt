package com.jap.twStockApp.ui.bottombar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import com.jap.twStockApp.R
import com.jap.twStockApp.databinding.MyBottomBarBinding

class MyBottomBar(context: Context, attributeSet: AttributeSet) : LinearLayoutCompat(context, attributeSet) {

    var navigationHomeEvent: (() -> Unit)? = null
    var navigationDashboardEvent: (() -> Unit)? = null
    var navigationFavoritesEvent: (() -> Unit)? = null

    private var homeSelect: Drawable? = null
    private var homeUnSelect: Drawable? = null
    private var dashboardSelect: Drawable? = null
    private var dashboardUnSelect: Drawable? = null
    private var favoritesSelect: Drawable? = null
    private var favoritesUnSelect: Drawable? = null

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val viewBinding = MyBottomBarBinding.inflate(layoutInflater, this, true)

    init {
        initDrawable()

        viewBinding.navigationHome.setOnClickListener {
            chooseHome()
            navigationHomeEvent?.invoke()
        }
        viewBinding.navigationDashboard.setOnClickListener {
            chooseDashboard()
            navigationDashboardEvent?.invoke()
        }
        viewBinding.navigationFavorites.setOnClickListener {
            chooseFavorite()
            navigationFavoritesEvent?.invoke()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initDrawable() {
        try {
            homeSelect = resources.getDrawable(R.drawable.ic_home_green_24dp, null)
            homeUnSelect = resources.getDrawable(R.drawable.ic_home_black_24dp, null)
            dashboardSelect = resources.getDrawable(R.drawable.ic_dashboard_green_24dp, null)
            dashboardUnSelect = resources.getDrawable(R.drawable.ic_dashboard_black_24dp, null)
            favoritesSelect = resources.getDrawable(R.drawable.ic_favorite_green_24dp, null)
            favoritesUnSelect = resources.getDrawable(R.drawable.ic_favorite_black_24dp, null)
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString())
        }
    }

    private fun chooseHome() {
        setHomeImageView(true)
        setDashBoardImageView(false)
        setFavoritesImageView(false)
    }

    private fun chooseDashboard() {
        setHomeImageView(false)
        setDashBoardImageView(true)
        setFavoritesImageView(false)
    }

    private fun chooseFavorite() {
        setHomeImageView(false)
        setDashBoardImageView(false)
        setFavoritesImageView(true)
    }


    private fun setHomeImageView(select: Boolean) {
        if (select) viewBinding.imageHome.setImageDrawable(homeSelect)
        else viewBinding.imageHome.setImageDrawable(homeUnSelect)
    }

    private fun setDashBoardImageView(select: Boolean) {
        if (select) viewBinding.imageDashboard.setImageDrawable(dashboardSelect)
        else viewBinding.imageDashboard.setImageDrawable(dashboardUnSelect)
    }

    private fun setFavoritesImageView(select: Boolean) {
        if (select) viewBinding.imageFavorite.setImageDrawable(favoritesSelect)
        else viewBinding.imageFavorite.setImageDrawable(favoritesUnSelect)
    }
}