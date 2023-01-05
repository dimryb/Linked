package ru.netology.linked.domain

import ru.netology.linked.domain.dto.Authentication

interface AuthRepository {
    suspend fun authentication(authentication: Authentication)
    suspend fun registration(login: String, password: String, name: String, file: String? = null)
}