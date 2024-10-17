package com.azercell.domain.usecases

import com.azercell.domain.repositories.CurrencyRepository
import com.azercell.domain.repositories.RateGuardRepository
import javax.inject.Inject

class CheckAndUpdateRateGuardUseCase @Inject constructor(
    private val rateGuardRepository: RateGuardRepository
) {

    operator fun invoke():Int{
        return rateGuardRepository.checkAndUpdateRateGuards()
    }
}