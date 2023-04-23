package it.macgood.hotelsapp.data.api

import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.Hotel
import it.macgood.hotelsapp.presentation.ui.details.entyties.HotelDescription
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelsApi {

    @GET("0777.json")
    suspend fun getHotels(): Response<List<Hotel>>

    @GET("{hotelId}.json")
    suspend fun getHotelDescription(
        @Path("hotelId") hotelId: String
    ): Response<HotelDescription>

}