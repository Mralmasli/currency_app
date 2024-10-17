package com.azercell.domain.usecases

import com.azercell.domain.repositories.RateGuardRepository
import javax.inject.Inject

class GetAllRateGuardUseCase @Inject constructor(
    private val rateGuardRepository: RateGuardRepository
) {

    operator fun invoke() = rateGuardRepository.getAllRateGuards()
}