package ru.netology.linked.presentation.model

import ru.netology.linked.domain.dto.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false,
)
