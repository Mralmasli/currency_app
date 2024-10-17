package com.azercell.domain.usecases

import com.azercell.domain.model.ResultWrapper
import com.azercell.domain.repositories.CurrencyRepository
import java.math.BigDecimal
import javax.inject.Inject

class ExchangeUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    data class Param(
        val from: String,
        val to: String,
    )

    suspend operator fun invoke(param: Param): ResultWrapper<BigDecimal> {
        return currencyRepository.exchange(param.from, param.to)
    }
}