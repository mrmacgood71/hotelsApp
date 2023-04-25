package it.macgood.hotelsapp.domain.repository

import it.macgood.hotelsapp.domain.entyties.Hotel
import it.macgood.hotelsapp.domain.entyties.HotelDescription
import retrofit2.Response

interface HotelsRepository {

    suspend fun getHotels() : Response<List<Hotel>>

    suspend fun getHotelDescription(hotelId: String) : Response<HotelDescription>

}