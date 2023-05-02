package it.macgood.hotelsapp.domain.usecase

import it.macgood.hotelsapp.domain.repository.HotelsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHotelUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend fun execute(hotelId: String) = repository.getHotelDescription(hotelId)
}