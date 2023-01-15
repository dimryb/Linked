package ru.netology.linked.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.linked.domain.dto.Post

class FeedDiffCallback  : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}