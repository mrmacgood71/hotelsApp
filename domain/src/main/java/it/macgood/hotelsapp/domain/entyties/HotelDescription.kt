package it.macgood.hotelsapp.domain.entyties

import com.google.gson.annotations.SerializedName

data class HotelDescription(
    val id: Int,
    val address: String,
    val distance: Double,
    val image: String,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double,
    val name: String,
    val stars: Double,
    @SerializedName("suites_availability")
    var suitesAvailability: String
) {
    fun countAvailable(suitesAvailability: String) {
        var count = 0
        suitesAvailability.split(":").forEach{
            if (it.isNotBlank()) count += it.toInt()
        }
        this.suitesAvailability = count.toString()
    }
}