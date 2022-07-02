package com.jap.twStockApp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jap.twStockApp.databinding.ActivityStartBinding
import com.jap.twStockApp.ui.base.BaseFragmentViewModelFactory
import com.jap.twStockApp.util.FragmentSwitchUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
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

        var trigger = false
        baseViewModel?.loadingBarPercentLiveData?.observe(this) { loadingBarPercent ->
            if (trigger) return@observe
            if (loadingBarPercent >= 1f) {
                trigger = true
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                binding.loadingBar.setSmoothPercent(loadingBarPercent.toFloat())
                binding.tvLoadingBar.text = String.format(Locale.getDefault(), "%.0f", loadingBarPercent * 100) + " %"
            }
        }
        baseViewModel?.updateAllData()
    }

}
