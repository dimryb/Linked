package ru.netology.linked.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.entity.PostEntity
import ru.netology.linked.data.entity.toDto
import ru.netology.linked.data.entity.toEntity
import ru.netology.linked.data.error.ApiError
import ru.netology.linked.data.error.NetworkError
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.Job
import ru.netology.linked.domain.dto.Post
import java.io.IOException
import javax.inject.Inject
import ru.netology.linked.data.error.UnknownError

class RepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val apiService: ApiService,
) : Repository {

    override val data = postDao.getPosts().map(List<PostEntity>::toDto).flowOn(Dispatchers.Default)

    override suspend fun getEvents() {
        TODO("Not yet implemented")
    }

    override suspend fun setEvent(event: Event) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun removeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventsAfter(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventsBefore(count: Int, eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun likeEvent(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventsNewer(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getEventParticipants(eventId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun saveMedia(file: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getJobs() {
        TODO("Not yet implemented")
    }

    override suspend fun setJob(job: Job) {
        TODO("Not yet implemented")
    }

    override suspend fun removeJob(jobId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getMyWall() {
        TODO("Not yet implemented")
    }

    override suspend fun getMyWallLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getMyWallAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getMyWallBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getMyWallNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getPosts() {
        try {
            val response = apiService.getPosts()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertPosts(body.toEntity())
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
            postDao.insertPost(PostEntity.fromDto(body))
        }catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPostsLatest(count: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun removePost(postId: Long) {
        try {
            val response = apiService.removePost(postId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            postDao.removePost(postId)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPostsAfter(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsBefore(count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun likePost(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers() {
        TODO("Not yet implemented")
    }

    override suspend  fun getUser(userId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getWall(authorId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getWallLatest(authorId: Long, count: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getWallAfter(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getWallBefore(authorId: Long, count: Int, postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend  fun getWallNewer(authorId: Long, postId: Long) {
        TODO("Not yet implemented")
    }
}