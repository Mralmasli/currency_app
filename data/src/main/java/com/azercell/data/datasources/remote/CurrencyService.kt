package com.azercell.data.datasources.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigDecimal

interface CurrencyService {

    @GET("/listquotes")
    suspend fun getCurrencyList(): Response<List<String>>

    @GET("/exchange")
    suspend fun exchange(
        @Query("from") from: String,
        @Query("to") to: String,
    ): Response<BigDecimal>
}
