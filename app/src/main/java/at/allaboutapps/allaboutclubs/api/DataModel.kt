package at.allaboutapps.allaboutclubs.api

import android.util.Log
import at.allaboutapps.allaboutclubs.APP_TAG
import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.api.retrofit.ApiInterface
import io.reactivex.Single
import java.util.*

class DataModel(private val apiInterface: ApiInterface) {

    private var cachedClubList: LinkedList<Club>? = null

    fun fetchClubsList(forceFetch: Boolean): Single<LinkedList<Club>> {
        return if (forceFetch || cachedClubList == null)
            apiInterface.getClubList()
                    .doOnError { Log.e(APP_TAG, "${DataModel::class.simpleName}: ${it.localizedMessage}") }
                    .doOnSuccess { cachedClubList = it }
        else
            Single.just(cachedClubList)
    }
}