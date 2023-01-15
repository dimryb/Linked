package ru.netology.linked.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
//    @Embedded
//    val coords: Coordinates? = null,
    val link: String? = null,
//    val linkedOwnerIds: List<Long> = emptyList(),
//    val mentionIds: List<Long> = emptyList(),
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
//    @Embedded
//    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    //val users: Users,
){
    fun toDto(): Post = Post(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        authorJob = authorJob,
        content = content,
        published = published,
        //coords = coords,
        link = link,
//        linkedOwnerIds = linkedOwnerIds,
//        mentionIds = mentionIds,
        mentionedMe = mentionedMe,
        likedByMe = likedByMe,
//        attachment = attachment,
        ownedByMe = ownedByMe,
        users = Users(UserPreview("")),
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
                    //coords = coords,
                    link = link,
                    //linkedOwnerIds = linkedOwnerIds,
                    //mentionIds = mentionIds,
                    mentionedMe = mentionedMe,
                    likedByMe = likedByMe,
                    //attachment = attachment,
                    ownedByMe = ownedByMe,
                    //users = users,
                )
            }
    }
}

fun List<PostEntity>.toDto(): List<Post> = map(PostEntity::toDto)
fun List<Post>.toEntity(): List<PostEntity> = map(PostEntity::fromDto)
