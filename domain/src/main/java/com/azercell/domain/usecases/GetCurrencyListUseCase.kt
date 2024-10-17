package com.azercell.domain.usecases

import com.azercell.domain.model.ResultWrapper
import com.azercell.domain.repositories.CurrencyRepository
import javax.inject.Inject

class GetCurrencyListUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {
    suspend operator fun invoke(): ResultWrapper<List<String>> {
        return currencyRepository.getCurrencyList()
    }
}