package ru.netology.linked.domain.dto

data class Post(
    override val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val published: String,
    val coords: Coordinates? = null,
    val link: String? = null,
    val likeOwnerIds: List<Long> = emptyList(),
    val mentionIds: List<Long> = emptyList(),
    val mentionedMe: Boolean,
    val likedByMe: Boolean,
    val attachment: Attachment? = null,
    val ownedByMe: Boolean,
    val users: Users,

    val likes: Long = 0,
) : FeedItem()
