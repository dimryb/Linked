package ru.netology.linked.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.linked.domain.dto.*

interface Repository {

    val data: Flow<PagingData<FeedItem>>

    // Events
    suspend fun getEvents()
    suspend fun setEvent(event: Event)
    suspend fun getEventsLatest(count: Int)
    suspend fun getEvent(eventId: Long)
    suspend fun removeEvent(eventId: Long)
    suspend fun getEventsAfter(count: Int, eventId: Long)
    suspend fun getEventsBefore(count: Int, eventId: Long)
    suspend fun likeEvent(eventId: Long)
    suspend fun getEventsNewer(eventId: Long)
    suspend fun getEventParticipants(eventId: Long)

    // Media
    suspend fun saveMedia(file: String)

    // Jobs
    suspend fun getJobs()
    suspend fun setJob(job: Job)
    suspend fun removeJob(jobId: Long)

    // MyWall
    suspend fun getMyWall()
    suspend fun getMyWallLatest(count: Int)
    suspend fun getMyWallAfter(count: Int, postId: Long)
    suspend fun getMyWallBefore(count: Int, postId: Long)
    suspend fun getMyWallNewer(postId: Long)

    // Posts
    suspend fun getPosts()
    suspend fun setPost(post: Post)
    suspend fun getPostsLatest(count: Int)
    suspend fun getPost(postId: Long)
    suspend fun removePost(postId: Long)
    suspend fun getPostsAfter(count: Int, postId: Long)
    suspend fun getPostsBefore(count: Int, postId: Long)
    suspend fun likePost(post: Post)
    suspend fun getPostsNewer(postId: Long)

    // Users
    suspend fun getUsers()
    // fun authentication(authentication: Authentication)
    // fun registration(login: String, password: String, name: String, file: String? = null)
    suspend fun getUser(userId: Long)

    // Wall
    suspend fun getWall(authorId: Long)
    suspend fun getWallLatest(authorId: Long, count: Int)
    suspend fun getWallAfter(authorId: Long, count: Int, postId: Long)
    suspend fun getWallBefore(authorId: Long, count: Int, postId: Long)
    suspend fun getWallNewer(authorId: Long, postId: Long)
}