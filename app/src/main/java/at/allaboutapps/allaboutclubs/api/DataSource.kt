package at.allaboutapps.allaboutclubs.api

import at.allaboutapps.allaboutclubs.api.models.Club
import at.allaboutapps.allaboutclubs.api.retrofit.ApiInterface
import io.reactivex.Single
import java.util.*

class DataSource(val apiInterface: ApiInterface) {

    private var cachedClubList: LinkedList<Club>? = null

    fun fetchClubsList(forceFetch: Boolean): Single<LinkedList<Club>> {
        return if (forceFetch || cachedClubList == null)
            apiInterface.getClubList().doOnSuccess { cachedClubList = it }
        else
            Single.just(cachedClubList)
    }
}