package com.jap.twstockapp.Repository

import android.content.Context
import com.jap.twstockapp.roomdb.AppDatabase
import com.jap.twstockapp.roomdb.Favorite
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class FavoritesRespository {
    fun loadInfo(applicationContext : Context, task: FavoriteTaskFinish){
        Executors.newSingleThreadExecutor().submit {

            var temp_array = arrayListOf<Favorite>()

            val observable = Observable.create<Favorite> {
                val list_favorites = AppDatabase.getInstance(applicationContext).FavoriteDao().getAll()
                for (i in list_favorites!!) {
                    it.onNext(i)
                }
                it.onComplete()
            }

            val observer: Observer<Favorite> = object : Observer<Favorite> {
                override fun onNext(item: Favorite) {
                    temp_array.add(item)
                }
                override fun onError(e: Throwable) {
                    println("Error Occured ${e.message}")
                }
                override fun onComplete() {
                    AppDatabase.destroyInstance()
                    task.onFinish(temp_array)
                }

                override fun onSubscribe(d: Disposable) {
                }
            }

            observable.subscribeOn(Schedulers.newThread())
                        .subscribe(observer)
        }
    }
}

interface FavoriteTaskFinish{
    fun onFinish(data: ArrayList<Favorite>)
}