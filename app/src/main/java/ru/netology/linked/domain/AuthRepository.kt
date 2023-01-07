package ru.netology.linked.domain

import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Token

interface AuthRepository {
    suspend fun authentication(authentication: Authentication): Token
    suspend fun registration(
        login: String,
        password: String,
        name: String,
        file: String? = null
    ): Token
}