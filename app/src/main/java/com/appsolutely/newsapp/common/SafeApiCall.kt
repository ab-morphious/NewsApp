package com.appsolutely.newsapp.common

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
    return try {
        Result.Success(apiCall())
    } catch (e: IOException) {
        Result.Error(ErrorType.NetworkError, "You're offline. Please check your internet connection and try again.")
    } catch (e: HttpException) {
        val errorType = when (e.code()) {
            401 -> ErrorType.Unauthorized
            404 -> ErrorType.NotFound
            in 500..599 -> ErrorType.ServerError
            else -> ErrorType.CustomError(e.code(), e.message ?: "Something went wrong on the server.")
        }
        val errorMessage = when (e.code()) {
            401 -> "You're not authorized to access this content."
            404 -> "Oops! The content you're looking for isn't available."
            in 500..599 -> "We're experiencing some technical difficulties. Please try again later."
            else -> "Something went wrong. Please try again."
        }
        Result.Error(errorType, errorMessage)
    } catch (e: Exception) {
        Result.Error(ErrorType.UnknownError, "An unexpected error occurred. Please restart the app and try again.")
    }
}

sealed class ErrorType {
    data object NetworkError : ErrorType()
    data object ServerError : ErrorType()
    data object NotFound : ErrorType()
    data object Unauthorized : ErrorType()
    data object UnknownError : ErrorType()

    data class CustomError(val code: Int, val details: String) : ErrorType()
}
