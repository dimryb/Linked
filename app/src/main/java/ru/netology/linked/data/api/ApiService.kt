package ru.netology.linked.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import ru.netology.linked.domain.dto.*

interface ApiService {

    // Events
    @GET("events/")
    suspend fun getEvents(): Response<List<Event>>

    @POST("events/")
    suspend fun setEvent(@Body eventCreate: EventCreate): Response<Event>

    @GET("events/latest/")
    suspend fun getEventsLatest(@Query("count") count: Int): Response<List<Event>>

    @GET("events/{event_id}/")
    suspend fun getEvent(@Path("event_id") eventId: Long): Response<List<Event>>

    @DELETE("events/{event_id}/")
    suspend fun removeEvent(@Path("event_id") eventId: Long): Response<Unit>

    @GET("events/{event_id}/after/")
    suspend fun getEventsAfter(@Query("count") count: Int, @Path("event_id") eventId: Long): Response<List<Event>>

    @GET("events/{event_id}/before/")
    suspend fun getEventsBefore(@Query("count") count: Int, @Path("event_id") eventId: Long): Response<List<Event>>

    @POST("events/{event_id}/likes/")
    suspend fun likeEvent(@Path("event_id") eventId: Long): Response<Event>

    @DELETE("events/{event_id}/likes/")
    suspend fun dislikeEvent(@Path("event_id") eventId: Long): Response<Event>

    @GET("events/{event_id}/likes/")
    suspend fun getEventsNewer(@Path("event_id") eventId: Long): Response<List<Event>>

    @POST("events/{event_id}/participants/")
    suspend fun getEventParticipants(@Path("event_id") eventId: Long): Response<Event>

    @DELETE("events/{event_id}/participants/")
    suspend fun removeEventParticipants(@Path("event_id") eventId: Long): Response<Event>

    // Media
    @POST("media/")
    suspend fun saveMedia(@Part part: MultipartBody.Part): Response<Media>

    // Jobs
    @GET("my/jobs/")
    suspend fun getJobs(): Response<Job>

    @POST("my/jobs/")
    suspend fun setJob(@Body job: Job): Response<Job>

    @DELETE("my/jobs/{job_id}/")
    suspend fun removeJob(@Path("job_id") jobId: Long): Response<Unit>

    @GET("{user_id}/jobs/")
    suspend fun getUserJob(@Path("user_id") userId: Long): Response<Job>

    // MyWall
    @GET("my/wall/")
    suspend fun getMyWall(): Response<List<Post>>

    @GET("my/wall/latest/")
    suspend fun getMyWallLatest(@Query("count") count: Int): Response<List<Post>>

    @GET("my/wall/{post_id}/after/")
    suspend fun getMyWallAfter(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("my/wall/{post_id}/before/")
    suspend fun getMyWallBefore(
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("my/wall/{post_id}/newer/")
    suspend fun getMyWallNewer(@Path("post_id") postId: Long): Response<List<Post>>

    // Posts
    @GET("posts/")
    suspend fun getPosts(): Response<List<Post>>

    @POST("posts/")
    suspend fun setPost(@Body post: Post): Response<Post>

    @GET("posts/latest/")
    suspend fun getPostsLatest(@Query("count") count: Int): Response<List<Post>>

    @GET("posts/{post_id}/")
    suspend fun getPost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}/")
    suspend fun removePost(@Path("post_id") id: Long): Response<Unit>

    @GET("posts/{post_id}/after/")
    suspend fun getPostsAfter(
        @Path("post_id") postId: Long,
        @Query("count") count: Int,
    ): Response<List<Post>>

    @GET("posts/{post_id}/before/")
    suspend fun getPostsBefore(
        @Path("post_id") postId: Long,
        @Query("count") count: Int,
    ): Response<List<Post>>

    @POST("posts/{post_id}/likes/")
    suspend fun likePost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}/likes/")
    suspend fun dislikePost(@Path("post_id") postId: Long): Response<Post>

    @DELETE("posts/{post_id}/newer/")
    suspend fun getPostsNewer(@Path("post_id") postId: Long): Response<List<Post>>

    // Users
    @GET("users/")
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

    @GET("users/{user_id}/")
    suspend fun getUser(@Path("user_id") userId: Long): Response<User>

    // Wall
    @GET("{author_id}/wall/")
    suspend fun getWall(@Path("author_id") authorId: Long): Response<List<Post>>

    @GET("{author_id}/wall/latest/")
    suspend fun getWallLatest(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int
    ): Response<List<Post>>

    @GET("{author_id}/wall/{post_id}/after/")
    suspend fun getWallAfter(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("{author_id}/wall/{post_id}/before/")
    suspend fun getWallBefore(
        @Path("author_id") authorId: Long,
        @Query("count") count: Int,
        @Path("post_id") postId: Long
    ): Response<List<Post>>

    @GET("{author_id}/wall/{post_id}/newer/")
    suspend fun getWallNewer(
        @Path("author_id") authorId: Long,
        @Path("post_id") postId: Long
    ): Response<List<Post>>
}