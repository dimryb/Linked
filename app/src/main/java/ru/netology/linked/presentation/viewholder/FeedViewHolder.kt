package ru.netology.linked.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.Post

class FeedViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        setContent(post)
        setupClickListeners(post)
    }

    private fun setContent(post: Post) {
        binding.apply {
            authorTextView.text = post.author
            publishedTextView.text = post.published
            postTextView.text = post.content

            //avatarImageView
        }
    }

    private fun setupClickListeners(post: Post) {
        binding.apply {

        }
    }

}