package ru.netology.linked.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.error.ApiError
import ru.netology.linked.data.error.NetworkError
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.Job
import ru.netology.linked.domain.dto.Post
import java.io.IOException
import javax.inject.Inject
import ru.netology.linked.data.error.AppError
import ru.netology.linked.data.error.UnknownError

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : Repository {

    private val _data = MutableSharedFlow<List<Post>>(replay = 1)
    override val data: Flow<List<Post>>
        get() = _data

    override fun getEvents() {
        TODO("Not yet implemented")
    }

    override fun setEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override fun getEventsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun removeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsAfter(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsBefore(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun likeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventsNewer(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun getEventParticipants(eventId: Long) {
        TODO("Not yet implemented")
    }

    override fun saveMedia(file: String) {
        TODO("Not yet implemented")
    }

    override fun getJobs() {
        TODO("Not yet implemented")
    }

    override fun setJob(job: Job) {
        TODO("Not yet implemented")
    }

    override fun removeJob(jobId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWall() {
        TODO("Not yet implemented")
    }

    override fun getMyWallLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getMyWallAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWallBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getMyWallNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getPosts() {
        try {
            val response = apiService.getPosts()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            _data.tryEmit(body)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun setPost(post: Post) {
        try {
            val response = apiService.setPost(post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            // записать в dao
            getPosts() // Временно
        }catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override fun getPostsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override fun getPost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun removePost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun likePost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPostsNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getUsers() {
        TODO("Not yet implemented")
    }

//    override fun authentication(authentication: Authentication) {
//        TODO("Not yet implemented")
//    }
//
//    override fun registration(login: String, password: String, name: String, file: String?) {
//        TODO("Not yet implemented")
//    }

    override fun getUser(userId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWall(authorId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallLatest(authorId: Long, count: Int) {
        TODO("Not yet implemented")
    }

    override fun getWallAfter(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallBefore(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override fun getWallNewer(authorId: Long, postId: Long) {
        TODO("Not yet implemented")
    }
}