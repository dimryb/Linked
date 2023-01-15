package ru.netology.linked.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.netology.linked.data.dao.PostDao
import ru.netology.linked.data.dao.PostRemoteKeyDao
import ru.netology.linked.data.entity.PostEntity
import ru.netology.linked.data.entity.PostRemoteKeyEntity

@Database(
    entities = [PostEntity::class, PostRemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun postRemoteKeyDao(): PostRemoteKeyDao
}