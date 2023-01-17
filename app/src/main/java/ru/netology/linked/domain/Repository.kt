package ru.netology.linked.domain

import kotlinx.coroutines.flow.Flow
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.Job
import ru.netology.linked.domain.dto.Post

interface Repository {

    val data: Flow<List<Post>>

    // Events
    fun getEvents()
    fun setEvent(event: Event)
    fun getEventsLatest(count: Int)
    fun getEvent(eventId: Long)
    fun removeEvent(eventId: Long)
    fun getEventsAfter(count: Int, eventId: Long)
    fun getEventsBefore(count: Int, eventId: Long)
    fun likeEvent(eventId: Long)
    fun getEventsNewer(eventId: Long)
    fun getEventParticipants(eventId: Long)

    // Media
    fun saveMedia(file: String)

    // Jobs
    fun getJobs()
    fun setJob(job: Job)
    fun removeJob(jobId: Long)

    // MyWall
    fun getMyWall()
    fun getMyWallLatest(count: Int)
    fun getMyWallAfter(count: Int, postId: Long)
    fun getMyWallBefore(count: Int, postId: Long)
    fun getMyWallNewer(postId: Long)

    // Posts
    suspend fun getPosts()
    suspend fun setPost(post: Post)
    suspend fun getPostsLatest(count: Int)
    suspend fun getPost(postId: Long)
    suspend fun removePost(postId: Long)
    suspend fun getPostsAfter(count: Int, postId: Long)
    suspend fun getPostsBefore(count: Int, postId: Long)
    suspend fun likePost(postId: Long)
    suspend fun getPostsNewer(postId: Long)

    // Users
    fun getUsers()
    //fun authentication(authentication: Authentication)
    //fun registration(login: String, password: String, name: String, file: String? = null)
    fun getUser(userId: Long)

    // Wall
    fun getWall(authorId: Long)
    fun getWallLatest(authorId: Long, count: Int)
    fun getWallAfter(authorId: Long, count: Int, postId: Long)
    fun getWallBefore(authorId: Long, count: Int, postId: Long)
    fun getWallNewer(authorId: Long, postId: Long)
}