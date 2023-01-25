package ru.netology.linked.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.netology.linked.domain.dto.*

interface Repository {

    val data: Flow<PagingData<FeedItem>>
    val eventsDataPagingFlow: Flow<PagingData<FeedItem>>
    val usersDataFlow: Flow<List<User>>

    // Events
    suspend fun getEvents()
    suspend fun setEvent(event: Event, upload: MediaUpload?)
    suspend fun removeEvent(eventId: Long)
    suspend fun likeEvent(event: Event)

    // Media
    suspend fun saveMedia(upload: MediaUpload): Media

    // Posts
    suspend fun getPosts()
    suspend fun setPost(post: Post, upload: MediaUpload?)
    suspend fun removePost(postId: Long)
    suspend fun likePost(post: Post)

    // Users
    suspend fun getUsers()
}