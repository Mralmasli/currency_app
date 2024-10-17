package com.azercell.exchange.model

import az.unibank.coreui.state.State
import java.math.BigDecimal

data class ExchangeState(
    val isLoading: Boolean = true,
    val currencyList: List<String>? = null,
    val primaryCurrency: String = "USD",
    val secondaryCurrency: String = "USD",
    val exchangeValue: BigDecimal = BigDecimal.ONE,
    val primaryAmount: BigDecimal = BigDecimal.ZERO,
    val secondaryAmount: BigDecimal = BigDecimal.ZERO,
):State()
