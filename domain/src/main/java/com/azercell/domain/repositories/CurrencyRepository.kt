package com.azercell.domain.repositories

import com.azercell.domain.model.ResultWrapper
import java.math.BigDecimal

interface CurrencyRepository {

    suspend fun getCurrencyList(): ResultWrapper<List<String>>

    suspend fun exchange(from: String, to: String): ResultWrapper<BigDecimal>

}