package com.jap.twStockApp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context, private val application: Application) {

    private val appContext = context

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return appContext
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

//    @Singleton
//    @Provides
//    fun provideLoadingDialog(appContext : Context): LoadingDialog {
//        val loadingDialog = LoadingDialog(appContext , "正在更新...")//仅点击外部不可取消
//        loadingDialog.setCanceledOnTouchOutside(false)//点击返回键和外部都不可取消
//        loadingDialog.setCancelable(false)
//        return loadingDialog
//    }
}
