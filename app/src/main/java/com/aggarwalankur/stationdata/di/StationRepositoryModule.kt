package com.aggarwalankur.stationdata.di

import com.aggarwalankur.stationdata.data.StationRepository
import com.aggarwalankur.stationdata.data.StationRepositoryImpl
import com.aggarwalankur.stationdata.network.StationApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StationRepositoryModule {
    @Singleton
    @Provides
    fun provideStationRepositoryImpl(
        service: StationApiService
    ): StationRepository {
        return StationRepositoryImpl(service)
    }
}