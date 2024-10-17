package com.azercell.domain.model

sealed class ResultWrapper<out T> {
    data class Success<T>(val success: T) : ResultWrapper<T>()
    data class Error(
        val exception: Exception? = null,
        val message: String? = null,
    ) : ResultWrapper<Nothing>()
}