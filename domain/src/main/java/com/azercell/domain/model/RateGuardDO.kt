package com.azercell.domain.model

import java.math.BigDecimal

data class RateGuardDO(
    val id: Long = 0,
    val amountCurrency: String,
    val thresholdCurrency: String,
    val amount: BigDecimal,
    val threshold: BigDecimal,
    val previousValue: BigDecimal,
    val isActive: Boolean = true,
    val recordCount: Int = 0
)
