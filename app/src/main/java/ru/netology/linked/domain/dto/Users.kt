package ru.netology.linked.domain.dto

data class Users(
    val map: Map<Long, UserPreview>,
    val user: UserPreview,
)
