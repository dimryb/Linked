package ru.netology.linked.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.linked.domain.dto.*

interface Repository {

    val data: Flow<PagingData<FeedItem>>
    val eventsDataPagingFlow: Flow<PagingData<FeedItem>>

    // Events
    suspend fun getEvents()
    suspend fun setEvent(event: Event, upload: MediaUpload?)
    suspend fun getEvent(eventId: Long)
    suspend fun removeEvent(eventId: Long)
    suspend fun likeEvent(event: Event)
    suspend fun getEventsNewer(eventId: Long)
    suspend fun getEventParticipants(eventId: Long)

    // Media
    suspend fun saveMedia(upload: MediaUpload): Media

    // Jobs
    suspend fun getJobs()
    suspend fun setJob(job: Job)
    suspend fun removeJob(jobId: Long)

    // MyWall
    suspend fun getMyWall()
    suspend fun getMyWallNewer(postId: Long)

    // Posts
    suspend fun getPosts()
    suspend fun setPost(post: Post, upload: MediaUpload?)
    suspend fun getPost(postId: Long)
    suspend fun removePost(postId: Long)
    suspend fun likePost(post: Post)
    suspend fun getPostsNewer(postId: Long)

    // Users
    suspend fun getUsers()
    suspend fun getUser(userId: Long)

    // Wall
    suspend fun getWall(authorId: Long)
    suspend fun getWallNewer(authorId: Long, postId: Long)
}