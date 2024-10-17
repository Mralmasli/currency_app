package com.azercell.domain.repositories

import com.azercell.domain.model.RateGuardDO
import kotlinx.coroutines.flow.Flow

interface RateGuardRepository {

    fun createRateGuard(rateGuard : RateGuardDO)

    fun deleteRateGuard(rateGuard : RateGuardDO)

    fun getAllRateGuards(): Flow<List<RateGuardDO>>

    fun getAllRateGuardsSync(): List<RateGuardDO>

    fun updateRateGuard(rateGuard : RateGuardDO)

    fun getRateGuard(id: Long): Flow<RateGuardDO>

    fun checkAndUpdateRateGuards(): Int
}