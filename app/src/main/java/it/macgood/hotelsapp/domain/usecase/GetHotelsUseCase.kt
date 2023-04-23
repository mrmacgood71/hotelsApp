package it.macgood.hotelsapp.domain.usecase

import it.macgood.hotelsapp.data.repository.HotelsRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHotelsUseCase @Inject constructor(
    private val repositoryImpl: HotelsRepositoryImpl
) {
    suspend fun execute() = repositoryImpl.getHotels()
}