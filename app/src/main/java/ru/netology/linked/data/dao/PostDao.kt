package ru.netology.linked.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow
import ru.netology.linked.data.db.DbConverter
import ru.netology.linked.data.entity.EventEntity
import ru.netology.linked.data.entity.PostEntity
import ru.netology.linked.data.entity.UserEntity

@Dao
@TypeConverters(DbConverter::class)
interface PostDao {
    // events
    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getEvents(): Flow<List<EventEntity>>

    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getEventsPagingSource(): PagingSource<Int, EventEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertEvent(event: EventEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    // posts
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getPostsPagingSource(): PagingSource<Int, PostEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    suspend fun removePost(id: Long)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    suspend fun updateContentPostById(id: Long, content: String)

    suspend fun setPostContent(post: PostEntity) =
        if (post.id == 0L) insertPost(post) else updateContentPostById(post.id, post.content)

    //users
    @Query("SELECT * FROM UserEntity ORDER BY id DESC")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)
}