package ru.netology.linked.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.netology.linked.domain.dto.FeedItem
import ru.netology.linked.domain.dto.Post

class FeedDiffCallback  : DiffUtil.ItemCallback<FeedItem>() {
    override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean {
        return oldItem == newItem
    }
}