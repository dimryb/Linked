package ru.netology.linked.data.repository

import retrofit2.Response
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.error.ApiError
import ru.netology.linked.data.error.NetworkError
import ru.netology.linked.data.error.UnknownError
import ru.netology.linked.domain.AuthRepository
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Token
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : AuthRepository {
    override suspend fun authentication(authentication: Authentication): Token {
        try {
            val response = apiService.authentication(authentication)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: ApiError) {
            throw e
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun registration(
        login: String,
        password: String,
        name: String,
        file: String?
    ): Token {
        val response: Response<Token>
        try {
            response = apiService.registration(login, password, name)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: ApiError) {
            throw e
        } catch (e: Exception) {
            throw UnknownError
        }
    }
}