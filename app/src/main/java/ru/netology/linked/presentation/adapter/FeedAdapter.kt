package ru.netology.linked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.FeedItem
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.PostViewHolder
import ru.netology.linked.presentation.viewholder.OnInteractionListener

class FeedAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<FeedItem, RecyclerView.ViewHolder>(FeedDiffCallback()) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Post -> R.layout.card_post
            is Event -> R.layout.card_post
            null -> error("unknown item type")
            else -> error("unknown item type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Post -> (holder as? PostViewHolder)?.bind(item)
            is Event -> (holder as? PostViewHolder)?.bind(with(item) {
                Post(
                    id = id,
                    authorId = authorId,
                    author = author,
                    authorAvatar = authorAvatar,
                    authorJob = authorJob,
                    content = "Событие: $content",
                    published = published,
                    coords = coords,
                    link = link,
                    likeOwnerIds = likeOwnerIds,
                    mentionIds = emptyList(),
                    mentionedMe = false,
                    likedByMe = likedByMe,
                    attachment = null,
                    ownedByMe = ownerByMe,
                    users = users,
                )
            }
            )
            null -> error("unknown view type")
            else -> error("unknown item type")
        }
    }

}
