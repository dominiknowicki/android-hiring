package at.allaboutapps.allaboutclubs.api.retrofit

import at.allaboutapps.allaboutclubs.api.models.Club
import io.reactivex.Single
import retrofit2.http.GET
import java.util.*

interface ApiInterface {
    @GET("hiring/clubs.json")
    fun getClubList(): Single<LinkedList<Club>>
}