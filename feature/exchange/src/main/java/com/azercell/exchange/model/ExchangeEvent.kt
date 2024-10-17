package com.azercell.exchange.model

import java.math.BigDecimal

sealed interface ExchangeEvent  {

    data class SetAmount(val amount: BigDecimal) : ExchangeEvent

}