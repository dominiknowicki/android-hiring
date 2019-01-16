package at.allaboutapps.a3hiring.api.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Club(
        @JsonProperty("name")
        val name: String,
        @JsonProperty("country")
        val country: String,
        @JsonProperty("value")
        val value: Long,
        @JsonProperty("image")
        val image: String?) : Parcelable