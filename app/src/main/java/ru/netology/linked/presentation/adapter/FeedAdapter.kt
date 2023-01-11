package ru.netology.linked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import androidx.recyclerview.widget.ListAdapter
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.FeedViewHolder

class FeedAdapter(
    private val onInteractionListener: OnInteractionListener,
) : ListAdapter<Post, FeedViewHolder>(FeedDiffCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FeedViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}
