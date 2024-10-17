package com.azercell.domain.usecases

import com.azercell.domain.model.RateGuardDO
import com.azercell.domain.repositories.RateGuardRepository
import javax.inject.Inject

class DeleteRateGuardUseCase @Inject constructor(
    private val rateGuardRepository: RateGuardRepository
) {
    operator fun invoke(rateGuardDO: RateGuardDO){
        rateGuardRepository.deleteRateGuard(rateGuardDO)
    }
}