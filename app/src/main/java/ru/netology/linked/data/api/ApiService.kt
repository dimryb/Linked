package ru.netology.linked.data.api

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import ru.netology.linked.domain.dto.Token

interface ApiService {

    @FormUrlEncoded
    @POST("users/registration/")
    suspend fun registration(
        @Field("login") login: String,
        @Field("password") password : String,
        @Field("name") name: String,
        //@Field("file") file: File,
    ): Response<Token>
}