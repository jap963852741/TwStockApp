package com.jap.twStockApp.di.condition

import android.content.Context
import com.jap.twStockApp.Repository.StockInformationRepository
import com.jap.twStockApp.Repository.network.UpdateDataSource
import com.jap.twStockApp.util.dialog.LoadingDialog
import dagger.Module
import dagger.Provides

@ConditionScope
@Module
class ConditionModule(private val fragmentContext: Context) {

//    @Provides
//    fun provideHomeViewModelFactory(application: Application,
//                                    stockInformationRepository : StockInformationRepository): HomeViewModelFactory {
//        return HomeViewModelFactory(application, stockInformationRepository)
//    }
//
    @Provides
    fun provideStockInformationRepository(): StockInformationRepository {
        return StockInformationRepository(UpdateDataSource())
    }

//    @Provides
//    fun provideConditionLoadingDialog(): LoadingDialog {
//        val loadingDialog = LoadingDialog(fragmentContext , "正在比對...")//仅点击外部不可取消
//        loadingDialog.setCanceledOnTouchOutside(false)//点击返回键和外部都不可取消
//        loadingDialog.setCancelable(false)
//        return loadingDialog
//    }

    @Provides
    fun provideHomeLoadingDialog(): LoadingDialog {
        val loadingDialog = LoadingDialog(fragmentContext, "正在更新...") // 仅点击外部不可取消
        loadingDialog.setCanceledOnTouchOutside(false) // 点击返回键和外部都不可取消
        loadingDialog.setCancelable(false)
        return loadingDialog
    }
}
