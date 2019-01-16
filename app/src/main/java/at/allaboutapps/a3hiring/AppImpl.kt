package at.allaboutapps.a3hiring

import android.support.multidex.MultiDexApplication
import at.allaboutapps.a3hiring.api.retrofit.ApiInterface
import at.allaboutapps.a3hiring.api.retrofit.RetrofitImpl

class AppImpl : MultiDexApplication() {

    val apiInterface: ApiInterface by lazy {
        RetrofitImpl().apiInterface
    }
}