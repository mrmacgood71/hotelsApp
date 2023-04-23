package it.macgood.hotelsapp.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.macgood.hotelsapp.data.repository.HotelsRepositoryImpl
import it.macgood.hotelsapp.domain.repository.HotelsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun provideHotelsRepository(
        repository: HotelsRepositoryImpl
    ): HotelsRepository
}