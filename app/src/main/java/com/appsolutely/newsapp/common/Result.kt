package com.appsolutely.newsapp.common

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorType: ErrorType, val message: String? = null) : Result<Nothing>()

    fun isSuccess() = this is Success
    fun isError() = this is Error
    fun getOrNull(): T? = if (this is Success) data else null

    fun getOrDefault(defaultValue: @UnsafeVariance T): T {
        return when (this) {
            is Success -> data
            is Error -> defaultValue
        }
    }

    fun exceptionOrNull(): Throwable? {
        return when (this) {
            is Success -> null
            is Error -> Throwable(message)
        }
    }
}
