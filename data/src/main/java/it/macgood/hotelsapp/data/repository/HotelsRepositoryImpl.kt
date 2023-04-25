package it.macgood.hotelsapp.data.repository

import it.macgood.hotelsapp.data.api.HotelsApi
import it.macgood.hotelsapp.domain.repository.HotelsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HotelsRepositoryImpl @Inject constructor(
    private val api: HotelsApi
) : HotelsRepository {

    override suspend fun getHotels() = api.getHotels()

    override suspend fun getHotelDescription(hotelId: String) = api.getHotelDescription(hotelId)
}