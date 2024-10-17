package com.azercell.data.modules

import com.azercell.domain.model.ResultWrapper
import retrofit2.Response


inline fun <T> handleResult(
    apiCall: () -> Response<T>?
): ResultWrapper<T?> {
    return try {
        val result = apiCall()
        if (result?.isSuccessful == true) {
            ResultWrapper.Success(result.body())
        } else {
            ResultWrapper.Error(
                exception = Exception("Something went wrong"),
            )
        }
    } catch (throwable: Throwable) {
        ResultWrapper.Error(
            message = throwable.message,
        )

    }
}