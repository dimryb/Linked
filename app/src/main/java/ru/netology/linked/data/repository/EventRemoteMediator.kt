package ru.netology.linked.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import okio.IOException
import ru.netology.linked.data.api.ApiService
import ru.netology.linked.data.dao.EventRemoteKeyDao
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.db.AppDb
import ru.netology.linked.data.entity.EventEntity
import ru.netology.linked.data.entity.EventRemoteKeyEntity
import ru.netology.linked.data.error.ApiError

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val service: ApiService,
    private val postDao: PostDao,
    private val eventRemoteKeyDao: EventRemoteKeyDao,
    private val appDb: AppDb,
) : RemoteMediator<Int, EventEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        try {
            val response = when (loadType) {
                LoadType.REFRESH -> {
                    val maxId = eventRemoteKeyDao.max() ?: 0
                    service.getEventsAfter(maxId, state.config.pageSize)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val id = eventRemoteKeyDao.min() ?: return MediatorResult.Success(false)
                    service.getEventsBefore(id, state.config.pageSize)
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
                        val max = eventRemoteKeyDao.max() ?: afterId
                        val min = eventRemoteKeyDao.min() ?: beforeId
                        eventRemoteKeyDao.insert(
                            listOf(
                                EventRemoteKeyEntity(
                                    EventRemoteKeyEntity.KeyType.AFTER,
                                    if (afterId > max) afterId else max,
                                ),
                                EventRemoteKeyEntity(
                                    EventRemoteKeyEntity.KeyType.BEFORE,
                                    if (beforeId < min) beforeId else min,
                                )
                            )
                        )
                    }
                    LoadType.PREPEND -> {
                        eventRemoteKeyDao.insert(
                            EventRemoteKeyEntity(
                                EventRemoteKeyEntity.KeyType.AFTER,
                                body.first().id,
                            ),
                        )
                    }
                    LoadType.APPEND -> {
                        eventRemoteKeyDao.insert(
                            EventRemoteKeyEntity(
                                EventRemoteKeyEntity.KeyType.BEFORE,
                                body.last().id,
                            )
                        )
                    }
                }

                postDao.insertEvents(body.map(EventEntity::fromDto))
            }

            return MediatorResult.Success(body.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        }
    }
}