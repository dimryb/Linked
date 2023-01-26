package ru.netology.linked.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.linked.domain.dto.Attachment
import ru.netology.linked.domain.dto.Coordinates
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.domain.dto.UserPreview

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val published: String,
    @Embedded
    val coords: Coordinates?,
    val link: String?,
    val likeOwnerIds: List<Long> = emptyList(),
    val mentionIds: List<Long>,
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    @Embedded
    val attachment: Attachment?,
    val ownedByMe: Boolean,
    val users: Map<Long, UserPreview>,
) {
    fun toDto(): Post = Post(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        authorJob = authorJob,
        content = content,
        published = published,
        coords = coords,
        link = link,
        likeOwnerIds = likeOwnerIds,
        mentionIds = mentionIds,
        mentionedMe = mentionedMe,
        likedByMe = likedByMe,
        attachment = attachment,
        ownedByMe = ownedByMe,
        users = users,

        likes = likeOwnerIds.size.toLong(),
    )

    companion object {
        fun fromDto(post: Post): PostEntity =
            with(post) {
                return PostEntity(
                    id = id,
                    authorId = authorId,
                    author = author,
                    authorAvatar = authorAvatar,
                    authorJob = authorJob,
                    content = content,
                    published = published,
                    coords = coords,
                    link = link,
                    likeOwnerIds = likeOwnerIds,
                    mentionIds = mentionIds,
                    mentionedMe = mentionedMe,
                    likedByMe = likedByMe,
                    attachment = attachment,
                    ownedByMe = ownedByMe,
                    users = users,
                )
            }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)
