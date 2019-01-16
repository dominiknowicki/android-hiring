package at.allaboutapps.a3hiring.api.retrofit

import at.allaboutapps.a3hiring.api.models.Club
import io.reactivex.Single
import retrofit2.http.GET
import java.util.*

interface ApiInterface {
    @GET("hiring/clubs.json")
    fun getClubList(): Single<LinkedList<Club>>
}