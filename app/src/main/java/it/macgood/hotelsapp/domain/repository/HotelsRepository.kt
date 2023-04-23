package it.macgood.hotelsapp.domain.repository

import it.macgood.hotelsapp.presentation.ui.hotellist.entyties.Hotel
import it.macgood.hotelsapp.presentation.ui.details.entyties.HotelDescription
import retrofit2.Response

interface HotelsRepository {

    suspend fun getHotels() : Response<List<Hotel>>

    suspend fun getHotelDescription(hotelId: String) : Response<HotelDescription>

}