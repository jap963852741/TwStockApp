package com.jap.twStockApp.Repository

import android.content.Context
import com.jap.twStockApp.Repository.roomdb.AppDatabase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

class StockNoArrayRepository {
    fun loadInfo(applicationContext: Context, task: OnTaskFinish) {
        Executors.newSingleThreadExecutor().submit {
            var vocabulary = arrayListOf<String>()

            val observable = Observable.create<String> {
                val temp_arraylist = AppDatabase.getInstance(applicationContext)?.TwStockDao()?.getAllStockNo()
//                    Log.e("StockNoArrayRepository",temp_arraylist.toString())
                for (i in temp_arraylist!!) {
//                        Log.e("i",i.toString())
                    if (i != null)
                        it.onNext(i)
                }
                it.onComplete()
            }

            val observer: Observer<String> = object : Observer<String> {
                override fun onNext(item: String) {
                    vocabulary.add(item)
                }
                override fun onError(e: Throwable) {
                    println("Error Occured ${e.message}")
                }
                override fun onComplete() {
                    AppDatabase.destroyInstance()
                    task.onFinish(vocabulary)
                }

                override fun onSubscribe(d: Disposable) {
                }
            }

            observable.subscribeOn(Schedulers.newThread())
                .subscribe(observer)
        }
    }
}

interface OnTaskFinish {
    fun onFinish(data: ArrayList<String>)
}
