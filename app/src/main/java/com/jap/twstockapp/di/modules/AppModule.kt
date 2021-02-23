package com.jap.twstockapp.di.modules

import android.app.Application
import android.content.Context
import android.util.Log
import com.jap.twstockapp.di.home.HomeScope
import com.jap.twstockapp.util.dialog.LoadingDialog
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(context: Context , private val application: Application){

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

}