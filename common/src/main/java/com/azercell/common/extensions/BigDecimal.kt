package com.azercell.common.extensions

import com.azercell.common.constants.EMPTY
import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.format(): String {
    if(this > BigDecimal.ZERO || this < BigDecimal.ZERO) {
        return round().toPlainString()
    }
    return EMPTY
}

fun BigDecimal.round(): BigDecimal {
    return setScale(6, RoundingMode.HALF_EVEN).stripTrailingZeros()
}

