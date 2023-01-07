package ru.netology.linked.presentation.viewmodel

data class AuthError(
    val type: AuthErrorType, val message: String? = null
)

enum class AuthErrorType {
    UNKNOWN,
    PASSWORDS_DONT_MATCH,
    NAME_IS_BLANK,
    LOGIN_IS_BLANK,
    PASSWORD_IS_BLANK,
    API_ERROR,
}