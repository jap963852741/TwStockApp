package com.jap.twStockApp.di.home

import android.app.Application
import android.content.Context
import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.ui.home.HomeViewModelFactory
import com.jap.twStockApp.util.dialog.LoadingDialog
import dagger.Module
import dagger.Provides

@HomeScope
@Module
class HomeModule(fragmentContext: Context) {

    private val fragmentContext = fragmentContext

    @Provides
    fun provideHomeViewModelFactory(
        application: Application,
        stockInformationRepository: StockInformationRepository
    ): HomeViewModelFactory {
        return HomeViewModelFactory(application, stockInformationRepository)
    }

    @Provides
    fun provideStockInformationRepository(): StockInformationRepository {
        return StockInformationRepository(UpdateDataSource())
    }

    @Provides
    fun provideHomeLoadingDialog(): LoadingDialog {
        val loadingDialog = LoadingDialog(fragmentContext, "正在更新...") // 仅点击外部不可取消
        loadingDialog.setCanceledOnTouchOutside(false) // 点击返回键和外部都不可取消
        loadingDialog.setCancelable(false)
        return loadingDialog
    }
}
