package com.jap.twStockApp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.BuildConfig
import com.jap.twStockApp.databinding.ActivityStartBinding
import com.jap.twStockApp.ui.base.BaseFragmentViewModelFactory
import com.jap.twStockApp.util.*
import java.util.*

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    var baseViewModel: BaseViewModel? = null

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseViewModel = ViewModelProvider(this, BaseFragmentViewModelFactory())[BaseViewModel::class.java]

        if (BuildConfig.DEBUG) {
            goMainActivityAndFinish()
            return
        }
        val intervalTime = System.currentTimeMillis() - (SharedPreference.sharedPreferences?.getLong(APP_OPEN_TIMESTAMP, 0) ?: 0)
        if (intervalTime < oneMinuteMillis * 10) {
            goMainActivityAndFinish()
            return
        }
        else SharedPreference.sharedPreferences?.saveLong(APP_OPEN_TIMESTAMP, System.currentTimeMillis())

        var trigger = false
        baseViewModel?.loadingBarPercentLiveData?.observe(this) { loadingBarPercent ->
            if (trigger) return@observe
            if (loadingBarPercent >= 1f) {
                trigger = true
                goMainActivityAndFinish()
            } else {
                binding.loadingBar.setSmoothPercent(loadingBarPercent.toFloat())
                binding.tvLoadingBar.text = String.format(Locale.getDefault(), "%.0f", loadingBarPercent * 100) + " %"
            }
        }
        baseViewModel?.updateAllData()
    }

    private fun goMainActivityAndFinish() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
