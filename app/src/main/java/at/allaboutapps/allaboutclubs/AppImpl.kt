package at.allaboutapps.allaboutclubs

import android.support.multidex.MultiDexApplication
import at.allaboutapps.allaboutclubs.api.DataModel
import at.allaboutapps.allaboutclubs.api.retrofit.RetrofitImpl

const val APP_TAG = "AAA_AAC"

class AppImpl : MultiDexApplication() {

    val dataModel: DataModel by lazy {
        DataModel(RetrofitImpl().apiInterface)
    }
}