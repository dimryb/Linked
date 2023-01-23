package ru.netology.linked.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.linked.domain.dto.*

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorId: Long,
    val author: String,
    val authorAvatar: String? = null,
    val authorJob: String? = null,
    val content: String,
    val dateTime: String,
    val published: String,
    @Embedded
    val coords: Coordinates? = null,
    val eventType: Event.EventType,
    val likeOwnerIds: List<Long> = emptyList(),
    val likedByMe: Boolean,
    val speakerIds: List<Long> = emptyList(),
    val participatedByMe: Boolean,
    @Embedded
    val attachment: Attachment? = null,
    val link: String? = null,
    val ownerByMe: Boolean,
    val users: Map<Long, UserPreview>,
) {
    fun toDto(): Event = Event(
        id = id,
        authorId = authorId,
        author = author,
        authorAvatar = authorAvatar,
        authorJob = authorJob,
        content = content,
        datetime = dateTime,
        published = published,
        coords = coords,
        type = eventType,
        likeOwnerIds = likeOwnerIds,
        likedByMe = likedByMe,
        speakerIds = speakerIds,
        participatedByMe = participatedByMe,
        attachment = attachment,
        link = link,
        ownerByMe = ownerByMe,
        users = users,

        likes = likeOwnerIds.size.toLong(),
    )

    companion object {
        fun fromDto(event: Event): EventEntity =
            with(event) {
                return EventEntity(
                    id = id,
                    authorId = authorId,
                    author = author,
                    authorAvatar = authorAvatar,
                    authorJob = authorJob,
                    content = content,
                    dateTime = datetime,
                    published = published,
                    coords = coords,
                    eventType = type,
                    likeOwnerIds = likeOwnerIds,
                    likedByMe = likedByMe,
                    speakerIds = speakerIds,
                    participatedByMe = participatedByMe,
                    attachment = attachment,
                    link = link,
                    ownerByMe = ownerByMe,
                    users = users,
                )
            }
    }
}

fun List<EventEntity>.toDto(): List<Event> = map(EventEntity::toDto)
fun List<Event>.toEntity(): List<EventEntity> = map(EventEntity::fromDto)
