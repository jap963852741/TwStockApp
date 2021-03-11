package com.jap.twstockapp.di.home

import android.app.Application
import android.content.Context
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.network.UpdateDataSource
import com.jap.twstockapp.ui.home.HomeViewModelFactory
import com.jap.twstockapp.util.dialog.LoadingDialog
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@HomeScope
@Module
class HomeModule(fragmentContext : Context) {

    private val fragmentContext = fragmentContext

    @Provides
    fun provideHomeViewModelFactory(application: Application,
                                    stockInformationRepository : StockInformationRepository): HomeViewModelFactory {
        return HomeViewModelFactory(application, stockInformationRepository)
    }

    @Provides
    fun provideStockInformationRepository(): StockInformationRepository {
        return StockInformationRepository(UpdateDataSource())
    }

    @Provides
    fun provideHomeLoadingDialog(): LoadingDialog {
        val loadingDialog = LoadingDialog(fragmentContext , "正在更新...")//仅点击外部不可取消
        loadingDialog.setCanceledOnTouchOutside(false)//点击返回键和外部都不可取消
        loadingDialog.setCancelable(false)
        return loadingDialog
    }
}