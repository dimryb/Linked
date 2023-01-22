package ru.netology.linked.domain.dto

data class Event(
    override val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val datetime: String,
    val published: String,
    val coords: Coordinates? = null,
    val type: EventType,
    val likeOwnerIds: List<Long> = emptyList(),
    val likedByMe: Boolean,
    val speakerIds: List<Long> = emptyList(),
    val participatedByMe: Boolean,
    val attachment: Attachment? = null,
    val link: String? = null,
    val ownerByMe: Boolean,
    val users: Users,

    val likes: Long = 0,
) : FeedItem() {
    enum class EventType {
        OFFLINE,
        ONLINE,
    }
}
