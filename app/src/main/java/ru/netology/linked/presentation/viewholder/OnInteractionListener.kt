package ru.netology.linked.presentation.viewholder

import ru.netology.linked.domain.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onComment(post: Post, comment: String)
    fun onDetails(post: Post)
}