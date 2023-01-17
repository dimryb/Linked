package ru.netology.linked.data.api

import retrofit2.Response
import retrofit2.http.*
import ru.netology.linked.domain.dto.*

interface ApiService {

    // Events
//    suspend fun getEvents()
//    suspend fun setEvent(event: Event)
//    suspend fun getEventsLatest(count: Int)
//    suspend fun getEvent(eventId: Long)
//    suspend fun removeEvent(eventId: Long)
//    suspend fun getEventsAfter(count: Int, eventId: Long)
//    suspend fun getEventsBefore(count: Int, eventId: Long)
//    suspend fun likeEvent(eventId: Long)
//    suspend fun getEventsNewer(eventId: Long)
//    suspend fun getEventParticipants(eventId: Long)

    // Media
//    suspend fun saveMedia(file: String)

    // Jobs
//    suspend fun getJobs()
//    suspend fun setJob(job: Job)
//    suspend fun removeJob(jobId: Long)

    // MyWall
    @GET("api/my/wall/")
    suspend fun getMyWall(): Response<List<Post>>

    @GET("api/my/wall/latest/")
    suspend fun getMyWallLatest(@Query("count") count: Int): Response<List<Post>>

    @GET("api/my/wall/{post_id}/after/")
    suspend fun getMyWallAfter(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("api/my/wall/{post_id}/before/")
    suspend fun getMyWallBefore(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("api/my/wall/{post_id}/newer/")
    suspend fun getMyWallNewer(@Path("post_id") postId: Long): Response<List<Post>>

    // Posts
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>

    @POST("posts")
    suspend fun setPost(@Body post: Post): Response<Post>

    @GET("posts/latest")
    suspend fun getPostsLatest(@Query("count") count: Int): Response<List<Post>>

    @GET("posts/{post_id}")
    suspend fun getPost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}")
    suspend fun removePost(@Path("post_id") id: Long): Response<Unit>

    @GET("posts/{post_id}/after")
    suspend fun getPostsAfter(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("posts/{post_id}/before")
    suspend fun getPostsBefore(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @POST("posts/{post_id}/likes")
    suspend fun likePost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}/likes")
    suspend fun dislikePost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}/newer")
    suspend fun getPostsNewer(@Path("post_id") postId: Long): Response<List<Post>>

    // Users
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @FormUrlEncoded
    @POST("users/registration/")
    suspend fun registration(
        @Field("login") login: String,
        @Field("password") password: String,
        @Field("name") name: String,
        //@Field("file") file: File,
    ): Response<Token>

    @POST("users/authentication/")
    suspend fun authentication(@Body authentication: Authentication): Response<Token>

    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: Long): Response<User>

    // Wall
    @GET("api/{author_id}/wall/")
    suspend fun getWall(@Path("author_id") authorId: Long): Response<List<Post>>

    @GET("api/{author_id}/wall/latest/")
    suspend fun getWallLatest(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int
    ): Response<List<Post>>

    @GET("api/{author_id}/wall/{post_id}/after/")
    suspend fun getWallAfter(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("api/{author_id}/wall/{post_id}/before/")
    suspend fun getWallBefore(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("api/{author_id}/wall/{post_id}/newer/")
    suspend fun getWallNewer(
        @Path("author_id") authorId: Long,
        @Path("post_id") postId: Long
    ): Response<List<Post>>
}