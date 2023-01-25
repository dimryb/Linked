package ru.netology.linked.data.repository

import androidx.paging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.dao.EventRemoteKeyDao
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.dao.PostRemoteKeyDao
import ru.netology.linked.data.db.AppDb
import ru.netology.linked.data.entity.*
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

    override val usersDataFlow: Flow<List<User>> =
        postDao.getUsers().map(List<UserEntity>::toDto).flowOn(
            Dispatchers.Default
        )

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
            throw UnknownError
        }
    }

    override suspend fun setEvent(event: Event, upload: MediaUpload?) {
        try {
            val eventWithAttachment = upload?.let {
                saveMedia(it)
            }?.let {
                event.copy(attachment = Attachment(it.url, AttachmentType.IMAGE))
            }
            val response = apiService.setEvent((eventWithAttachment ?: event).toCreate())
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun removeEvent(eventId: Long) {
        try {
            val response = apiService.removeEvent(eventId)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            postDao.removeEvent(eventId)
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun likeEvent(event: Event) {
        try {
            val response = with(apiService) {
                if (event.likedByMe) ::dislikeEvent else ::likeEvent
            }(event.id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw ApiError(response.code(), response.message())
            postDao.insertEvent(EventEntity.fromDto(body))
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
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
}