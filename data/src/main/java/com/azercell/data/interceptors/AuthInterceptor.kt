package com.azercell.data.interceptors

import com.azercell.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest = request.newBuilder()
            .addHeader(X_RAPID_KEY, BuildConfig.X_RapidAPI_Key)
            .addHeader(HOST, HOST_VALUE)
            .build()

        return chain.proceed(newRequest)
    }

    companion object{
        private const val X_RAPID_KEY = "X-RapidAPI-Key"
        private const val HOST = "X-RapidAPI-Host"
        private const val HOST_VALUE = "currency-exchange.p.rapidapi.com"
    }
}