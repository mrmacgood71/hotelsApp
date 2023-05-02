package it.macgood.hotelsapp.domain.usecase

import it.macgood.hotelsapp.domain.repository.HotelsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHotelsUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend fun execute() = repository.getHotels()
}