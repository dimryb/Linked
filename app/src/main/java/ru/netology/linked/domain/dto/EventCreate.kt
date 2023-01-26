package ru.netology.linked.domain.dto

data class EventCreate(
    val id: Long,
    val content: String,
    val datetime: String? = null,
    val coords: Coordinates? = null,
    val type: EventType,
    val attachment: Attachment? = null,
    val link: String? = null,
    val speakerIds: List<Long>? = null,
)

fun Event.toCreate(): EventCreate = EventCreate(
    id = id,
    content = content,
    datetime = datetime,
    coords = coords,
    type = type,
    attachment = attachment,
    link = link,
    speakerIds = speakerIds,
)
