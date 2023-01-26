package ru.netology.linked.data.error

import java.io.IOException

sealed class AppError(var code: String) : RuntimeException() {
    companion object {
        fun from(e: Throwable): AppError = when (e) {
            is AppError -> e
            is IOException -> NetworkError
            else -> UnknownError
        }
    }
}

class ApiError(code: String) : AppError(code)
object NetworkError : AppError("error_network")
object UnknownError : AppError("error_unknown")