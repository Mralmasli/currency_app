package com.azercell.data.modules

import com.azercell.data.repositories.CurrencyRepositoryImpl
import com.azercell.data.repositories.RateGuardRepositoryImpl
import com.azercell.domain.repositories.CurrencyRepository
import com.azercell.domain.repositories.RateGuardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    fun bindRateGuardRepository(
        rateGuardRepositoryImpl: RateGuardRepositoryImpl
    ): RateGuardRepository


}
