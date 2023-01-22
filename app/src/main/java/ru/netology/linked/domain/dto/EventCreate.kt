package ru.netology.linked.domain.dto

data class EventCreate(
    val id: Long,
    val content: String,
    val dateTime: String? = null,
    val coords: Coordinates? = null,
    val type: String? = null,
    val attachment: Attachment? = null,
    val link: String? = null,
    val speakerId: Long? = null,
)
