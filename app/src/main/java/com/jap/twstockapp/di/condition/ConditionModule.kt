package com.jap.twstockapp.di.condition

import android.content.Context
import com.jap.twstockapp.Repository.StockInformationRepository
import com.jap.twstockapp.Repository.network.UpdateDataSource
import com.jap.twstockapp.util.dialog.LoadingDialog
import dagger.Module
import dagger.Provides

@ConditionScope
@Module
class ConditionModule() {

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
}