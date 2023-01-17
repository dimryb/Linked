package ru.netology.linked.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.netology.linked.data.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getPosts(): Flow<List<PostEntity>>

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
}