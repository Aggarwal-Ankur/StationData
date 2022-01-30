package com.aggarwalankur.stationdata.di

import com.aggarwalankur.stationdata.data.FakeRepository
import com.aggarwalankur.stationdata.data.StationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


/**
 * TasksRepository binding to use in tests.
 *
 * Hilt will inject a [FakeRepository] instead of a [StationRepositoryImpl].
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StationRepositoryModule::class]
)
abstract class TestStationRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepository(repo: FakeRepository): StationRepository
}