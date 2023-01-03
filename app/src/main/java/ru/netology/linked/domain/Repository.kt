package ru.netology.linked.domain

import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.Job
import ru.netology.linked.domain.dto.Post

interface Repository {
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
    fun getPosts()
    fun setPost(post: Post)
    fun getPostsLatest(count: Int)
    fun getPost(postId: Long)
    fun removePost(postId: Long)
    fun getPostsAfter(count: Int, postId: Long)
    fun getPostsBefore(count: Int, postId: Long)
    fun likePost(postId: Long)
    fun getPostsNewer(postId: Long)

    // Users
    fun getUsers()
    fun authentication(authentication: Authentication)
    fun registration(login: String, password: String, name: String, file: String? = null)
    fun getUser(userId: Long)

    // Wall
    fun getWall(authorId: Long)
    fun getWallLatest(authorId: Long, count: Int)
    fun getWallAfter(authorId: Long, count: Int, postId: Long)
    fun getWallBefore(authorId: Long, count: Int, postId: Long)
    fun getWallNewer(authorId: Long, postId: Long)
}