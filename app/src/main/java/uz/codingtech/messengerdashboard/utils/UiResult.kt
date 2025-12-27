package uz.codingtech.messengerdashboard.utils

sealed class UiResult<out T> {
    data class Success<T>(val data: T) : UiResult<T>()
    data class Error(val message: ErrorType) : UiResult<Nothing>()
}

enum class ErrorType{
    UnAuthorized,
    UnExpected,
    BadRequest
}