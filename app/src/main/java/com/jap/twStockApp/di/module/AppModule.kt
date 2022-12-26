package com.jap.twStockApp.di.module

import android.content.Context
import com.jap.twStockApp.util.dialog.LoadingDialog
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val applicationModule = module {
//    factory { getLoadingDialog(androidContext()) }
}

fun getLoadingDialog(context: Context): LoadingDialog = LoadingDialog(context, "更新中...").apply {
    setCancelable(false)
    setCanceledOnTouchOutside(false)
}
