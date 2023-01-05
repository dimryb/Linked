package ru.netology.linked.data.repository

import ru.netology.linked.domain.AuthRepository
import ru.netology.linked.domain.dto.Authentication
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

) : AuthRepository {
    override suspend fun authentication(authentication: Authentication) {
        TODO("Not yet implemented")
    }

    override suspend fun registration(
        login: String,
        password: String,
        name: String,
        file: String?
    ) {
        TODO("Not yet implemented")
    }
}