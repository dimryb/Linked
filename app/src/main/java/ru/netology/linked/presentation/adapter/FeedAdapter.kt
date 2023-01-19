package ru.netology.linked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.FeedItem
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.FeedViewHolder
import ru.netology.linked.presentation.viewholder.OnInteractionListener

class FeedAdapter(
    private val onInteractionListener: OnInteractionListener,
) : PagingDataAdapter<FeedItem, RecyclerView.ViewHolder>(FeedDiffCallback()) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Post -> R.layout.card_post
            null -> error("unknown item type")
            else -> error("unknown item type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Post -> (holder as? FeedViewHolder)?.bind(item)
            null -> error("unknown view type")
            else -> error("unknown item type")
        }
    }

}
