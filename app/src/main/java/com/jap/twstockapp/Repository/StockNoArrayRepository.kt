package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.Repository.roomdb.AppDatabase
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class StockNoArrayRepository {
    fun loadInfo(applicationContext : Context,task: OnTaskFinish){
        Executors.newSingleThreadExecutor().submit {
            var vocabulary = arrayListOf<String>()

            val observable = Observable.create<String> {
                    val temp_arraylist = AppDatabase.getInstance(applicationContext).TwStockDao().getAllStockNo()
                    for (i in temp_arraylist!!) {
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