package it.macgood.hotelsapp.presentation.ui.details.entyties

import com.google.gson.annotations.SerializedName

data class HotelDescription(
    val address: String,
    val distance: Double,
    val id: Int,
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

}