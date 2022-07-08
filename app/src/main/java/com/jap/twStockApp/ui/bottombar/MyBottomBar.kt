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
        initClickListener()
        initChooseTag()
        chooseHome()
    }

    private fun initChooseTag() {
        viewBinding.imageHome.tag = false
        viewBinding.imageDashboard.tag = false
        viewBinding.imageFavorite.tag = false
    }

    private fun initClickListener() {
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

    fun chooseHome() {
        if (isHomeSelect()) return
        setHomeImageView(true)
        setDashBoardImageView(false)
        setFavoritesImageView(false)
    }

    fun chooseDashboard() {
        if (isDashBoardSelect()) return
        setHomeImageView(false)
        setDashBoardImageView(true)
        setFavoritesImageView(false)
    }

    fun chooseFavorite() {
        if (isFavoriteSelect()) return
        setHomeImageView(false)
        setDashBoardImageView(false)
        setFavoritesImageView(true)
    }


    private fun setHomeImageView(select: Boolean) {
        viewBinding.imageHome.tag = select
        if (select) viewBinding.imageHome.setImageDrawable(homeSelect)
        else viewBinding.imageHome.setImageDrawable(homeUnSelect)
    }

    private fun isHomeSelect(): Boolean = viewBinding.imageHome.tag as Boolean

    private fun setDashBoardImageView(select: Boolean) {
        viewBinding.imageDashboard.tag = select
        if (select) viewBinding.imageDashboard.setImageDrawable(dashboardSelect)
        else viewBinding.imageDashboard.setImageDrawable(dashboardUnSelect)
    }

    private fun isDashBoardSelect(): Boolean = viewBinding.imageDashboard.tag as Boolean

    private fun setFavoritesImageView(select: Boolean) {
        viewBinding.imageFavorite.tag = select
        if (select) viewBinding.imageFavorite.setImageDrawable(favoritesSelect)
        else viewBinding.imageFavorite.setImageDrawable(favoritesUnSelect)
    }

    private fun isFavoriteSelect(): Boolean = viewBinding.imageFavorite.tag as Boolean
}