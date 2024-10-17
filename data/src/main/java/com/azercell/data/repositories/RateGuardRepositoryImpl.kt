package com.azercell.data.repositories

import com.azercell.data.datasources.local.FakeRateGuardDB
import com.azercell.data.datasources.remote.CurrencyService
import com.azercell.data.model.toDO
import com.azercell.data.model.toEntity
import com.azercell.domain.model.RateGuardDO
import com.azercell.domain.repositories.RateGuardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RateGuardRepositoryImpl @Inject constructor(
    private val fakeDB: FakeRateGuardDB,
    private val currencyService: CurrencyService
) : RateGuardRepository {
    override fun createRateGuard(rateGuard: RateGuardDO) {
        fakeDB.create(rateGuard.toEntity())
    }

    override fun deleteRateGuard(rateGuard: RateGuardDO) {
        fakeDB.delete(rateGuard.toEntity())
    }

    override fun getAllRateGuards(): Flow<List<RateGuardDO>> {
        return fakeDB.getAllGuard().map { item -> item.map { it.toDO() } }
    }

    override fun getAllRateGuardsSync(): List<RateGuardDO> {
        return fakeDB.getAllGuardSync().map { item -> item.toDO() }
    }

    override fun updateRateGuard(rateGuard: RateGuardDO) {
        fakeDB.update(rateGuard.toEntity())
    }

    override fun getRateGuard(id: Long): Flow<RateGuardDO> {
        return flow {
            emit(fakeDB.getRateGuardWithId(id).toDO())
        }
    }

    override fun checkAndUpdateRateGuards():Int {
        //todo getAllRateGuard
        //todo getCurrent currency
        //todo filter is Active or not
        //todo check current value(with exchange) old one if value over min or max increase recordCount and update value
        return 1
    }
}