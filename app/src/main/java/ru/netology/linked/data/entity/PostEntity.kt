package ru.netology.linked.data.entity

import androidx.room.*
import ru.netology.linked.domain.dto.*

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
//    @Embedded
//    val mentionIds: List<Long>?, //TODO: сделать правильно
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    @Embedded
    val attachment: Attachment?,
    val ownedByMe: Boolean,
    //val users: Users, //TODO: сделать правильно
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
//        mentionIds = mentionIds, //TODO: сделать правильно
        mentionedMe = mentionedMe,
        likedByMe = likedByMe,
        attachment = attachment,
        ownedByMe = ownedByMe,
        users = Users(UserPreview("")),

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
                    //mentionIds = mentionIds, //TODO: сделать правильно
                    mentionedMe = mentionedMe,
                    likedByMe = likedByMe,
                    attachment = attachment,
                    ownedByMe = ownedByMe,
                    //users = users, //TODO: сделать правильно
                )
            }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)
