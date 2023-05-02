package it.macgood.hotelsapp.domain.entyties

import com.google.gson.annotations.SerializedName

data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val stars: Double,
    val distance: Double,
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