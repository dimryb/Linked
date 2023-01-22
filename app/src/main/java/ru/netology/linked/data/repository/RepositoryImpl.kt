package ru.netology.linked.data.repository

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.dao.EventRemoteKeyDao
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.dao.PostRemoteKeyDao
import ru.netology.linked.data.db.AppDb
import ru.netology.linked.data.entity.EventEntity
import ru.netology.linked.data.entity.PostEntity
import ru.netology.linked.data.entity.toEntity
import ru.netology.linked.data.error.ApiError
import ru.netology.linked.data.error.NetworkError
import ru.netology.linked.data.error.UnknownError
import ru.netology.linked.domain.Repository
import ru.netology.linked.domain.dto.*
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val postDao: PostDao,
    private val apiService: ApiService,
    postRemoteKeyDao: PostRemoteKeyDao,
    eventRemoteKeyDao: EventRemoteKeyDao,
    appDb: AppDb,
) : Repository {

    @OptIn(ExperimentalPagingApi::class)
    override val data: Flow<PagingData<FeedItem>> = Pager<Int, PostEntity>(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = postDao::getPostsPagingSource,
        remoteMediator = PostRemoteMediator(
            service = apiService,
            postDao = postDao,
            postRemoteKeyDao = postRemoteKeyDao,
            appDb = appDb,
        )
    ).flow.map { pagingData ->
        pagingData.map(PostEntity::toDto)
    }

    @OptIn(ExperimentalPagingApi::class)
    override val eventsDataPagingFlow: Flow<PagingData<FeedItem>> = Pager<Int, EventEntity>(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = postDao::getEventsPagingSource,
        remoteMediator = EventRemoteMediator(
            service = apiService,
            postDao = postDao,
            eventRemoteKeyDao = eventRemoteKeyDao,
            appDb = appDb,
        )
    ).flow.map { pagingData ->
        pagingData.map(EventEntity::toDto)
    }

    override suspend fun getEvents() {
        try {
            val response = apiService.getEvents()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            val events = body.toEntity()
            postDao.insertEvents(events)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw e
            //throw UnknownError
        }
    }

    override suspend fun setEvent(event: Event, upload: MediaUpload?) {
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

    override suspend fun saveMedia(upload: MediaUpload): Media {
        try {
            val part = MultipartBody.Part.createFormData(
                "file", upload.file.name, upload.file.asRequestBody()
            )
            val response = apiService.saveMedia(part)
            return response.body() ?: throw ApiError(response.code(), response.message())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
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

    override suspend fun setPost(post: Post, upload: MediaUpload?) {
        try {
            val postWithAttachment = upload?.let {
                saveMedia(it)
            }?.let {
                post.copy(attachment = Attachment(it.url, AttachmentType.IMAGE))
            }
            val response = apiService.setPost(postWithAttachment ?: post)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertPost(PostEntity.fromDto(body))
        } catch (e: IOException) {
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

    override suspend fun likePost(post: Post) {
        try {
            val response = with(apiService) {
                if (post.likedByMe) ::dislikePost else ::likePost
            }(post.id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertPost(PostEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getPostsNewer(postId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers() {
        try {
            val response = apiService.getUsers()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertUsers(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun getUser(userId: Long) {
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

    override suspend fun getWallNewer(authorId: Long, postId: Long) {
        TODO("Not yet implemented")
    }
}