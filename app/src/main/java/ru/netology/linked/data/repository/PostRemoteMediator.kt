package ru.netology.linked.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import okio.IOException
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.dao.PostRemoteKeyDao
import ru.netology.linked.data.db.AppDb
import ru.netology.linked.data.entity.PostEntity
import ru.netology.linked.data.entity.PostRemoteKeyEntity
import ru.netology.linked.data.error.ApiError

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val service: ApiService,
    private val postDao: PostDao,
    private val postRemoteKeyDao: PostRemoteKeyDao,
    private val appDb: AppDb,
) : RemoteMediator<Int, PostEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> {
                    //service.getLatest(state.config.pageSize)
                    val maxId = postRemoteKeyDao.max() ?: 0
                    service.getPostsAfter(maxId, state.config.pageSize)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val id = postRemoteKeyDao.min() ?: return MediatorResult.Success(false)
                    service.getPostsBefore(id, state.config.pageSize)
                }
            }

            if (!response.isSuccessful) {
                throw ApiError(response.message())
            }

            val body = response.body() ?: throw ApiError(
                response.message()
            )

            if (body.isEmpty()) return MediatorResult.Success(false)

            appDb.withTransaction {
                when (loadType) {
                    LoadType.REFRESH -> {
                        val afterId = body.first().id
                        val beforeId = body.last().id
                        val max = postRemoteKeyDao.max() ?: afterId
                        val min = postRemoteKeyDao.min() ?: beforeId
                        postRemoteKeyDao.insert(
                            listOf(
                                PostRemoteKeyEntity(
                                    PostRemoteKeyEntity.KeyType.AFTER,
                                    if (afterId > max) afterId else max,
                                ),
                                PostRemoteKeyEntity(
                                    PostRemoteKeyEntity.KeyType.BEFORE,
                                    if (beforeId < min) beforeId else min,
                                )
                            )
                        )
                    }
                    LoadType.PREPEND -> {
                        postRemoteKeyDao.insert(
                            PostRemoteKeyEntity(
                                PostRemoteKeyEntity.KeyType.AFTER,
                                body.first().id,
                            ),
                        )
                    }
                    LoadType.APPEND -> {
                        postRemoteKeyDao.insert(
                            PostRemoteKeyEntity(
                                PostRemoteKeyEntity.KeyType.BEFORE,
                                body.last().id,
                            )
                        )
                    }
                }

                postDao.insertPosts(body.map(PostEntity::fromDto))
            }

            return MediatorResult.Success(body.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }
}