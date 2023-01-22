package ru.netology.linked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardEventBinding
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.domain.dto.FeedItem
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.EventViewHolder
import ru.netology.linked.presentation.viewholder.PostViewHolder
import ru.netology.linked.presentation.viewholder.OnInteractionListener

class FeedAdapter(
    private val postOnInteractionListener: OnInteractionListener<Post>,
    private val eventOnInteractionListener: OnInteractionListener<Event>,
) : PagingDataAdapter<FeedItem, RecyclerView.ViewHolder>(FeedDiffCallback()) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Post -> R.layout.card_post
            is Event -> R.layout.card_event
            null -> error("unknown item type")
            else -> error("unknown item type")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.card_post -> {
                val binding =
                    CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PostViewHolder(binding, postOnInteractionListener)
            }
            R.layout.card_event -> {
                val binding =
                    CardEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                EventViewHolder(binding, eventOnInteractionListener)
            }
            else -> error("unknown view type: $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is Post -> (holder as? PostViewHolder)?.bind(item)
            is Event -> (holder as? EventViewHolder)?.bind(item)
            null -> error("unknown view type")
            else -> error("unknown item type")
        }
    }

}
