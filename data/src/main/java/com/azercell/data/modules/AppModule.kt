package com.azercell.data.modules

import com.azercell.data.datasources.local.FakeRateGuardDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFakeDb() = FakeRateGuardDB()
}