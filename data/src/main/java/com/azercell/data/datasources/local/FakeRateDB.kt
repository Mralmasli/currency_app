package com.azercell.data.datasources.local

import com.azercell.data.model.RateGuardEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRateGuardDB {
    fun create(rateGuardEntity: RateGuardEntity){}
    fun delete(rateGuardEntity: RateGuardEntity){}
    fun update(rateGuardEntity: RateGuardEntity){}
    fun getAllGuard():Flow<List<RateGuardEntity>>{
        return flow {
            emit(listOf())
        }
    }
    fun getAllGuardSync(): List<RateGuardEntity>{
        return listOf()
    }

    fun getRateGuardWithId(id:Long): RateGuardEntity{
        return RateGuardEntity()
    }

}

