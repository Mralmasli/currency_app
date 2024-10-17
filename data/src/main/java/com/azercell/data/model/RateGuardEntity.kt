package com.azercell.data.model

import com.azercell.domain.model.RateGuardDO
import java.math.BigDecimal

data class RateGuardEntity(
    val id: Long = 0,
    val amountCurrency: String = "",
    val thresholdCurrency: String= "",
    val amount: BigDecimal = BigDecimal.ZERO,
    val threshold: BigDecimal = BigDecimal.ZERO,
    val previousValue: BigDecimal = BigDecimal.ZERO,
    val isActive: Boolean = true,
    val recordCount: Int = 0
)

fun RateGuardDO.toEntity() = RateGuardEntity(
    id,
    amountCurrency,
    thresholdCurrency,
    amount,
    threshold,
    previousValue,
    isActive
)

fun RateGuardEntity.toDO() = RateGuardDO(id, amountCurrency, thresholdCurrency, amount, threshold, previousValue, isActive)
