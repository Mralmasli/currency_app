package com.azercell.data.repositories

import com.azercell.data.datasources.remote.CurrencyService
import com.azercell.data.modules.handleResult
import com.azercell.domain.model.ResultWrapper
import com.azercell.domain.repositories.CurrencyRepository
import java.math.BigDecimal
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override suspend fun getCurrencyList(): ResultWrapper<List<String>> {
        return when (val result = handleResult { currencyService.getCurrencyList() }) {
            is ResultWrapper.Error -> result
            is ResultWrapper.Success -> ResultWrapper.Success(result.success ?: listOf())
        }
    }

    override suspend fun exchange(from: String, to: String): ResultWrapper<BigDecimal> {
        if(from == to) return ResultWrapper.Success(BigDecimal.ONE)
        return when (val result = handleResult { currencyService.exchange(from,to) }) {
            is ResultWrapper.Error -> result
            is ResultWrapper.Success -> ResultWrapper.Success(result.success ?: BigDecimal.ONE)
        }
    }
}