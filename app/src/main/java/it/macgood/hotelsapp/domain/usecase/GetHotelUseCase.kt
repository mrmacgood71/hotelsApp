package it.macgood.hotelsapp.domain.usecase

import it.macgood.hotelsapp.data.repository.HotelsRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHotelUseCase @Inject constructor(
    private val repositoryImpl: HotelsRepositoryImpl
) {
    suspend fun execute(hotelId: String) = repositoryImpl.getHotelDescription(hotelId)
}