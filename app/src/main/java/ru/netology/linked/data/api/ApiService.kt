package ru.netology.linked.data.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.domain.dto.Token

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @FormUrlEncoded
    @POST("users/registration/")
    suspend fun registration(
        @Field("login") login: String,
        @Field("password") password : String,
        @Field("name") name: String,
        //@Field("file") file: File,
    ): Response<Token>

    @POST("users/authentication/")
    suspend fun authentication(@Body authentication: Authentication): Response<Token>
}