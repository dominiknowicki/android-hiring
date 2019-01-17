package at.allaboutapps.allaboutclubs

import android.support.multidex.MultiDexApplication
import at.allaboutapps.allaboutclubs.api.DataSource
import at.allaboutapps.allaboutclubs.api.retrofit.ApiInterface
import at.allaboutapps.allaboutclubs.api.retrofit.RetrofitImpl

class AppImpl : MultiDexApplication() {

    private var apiInterface: ApiInterface = RetrofitImpl().apiInterface
    lateinit var dataSource: DataSource

    override fun onCreate() {
        super.onCreate()
        dataSource = DataSource(apiInterface)
    }
}