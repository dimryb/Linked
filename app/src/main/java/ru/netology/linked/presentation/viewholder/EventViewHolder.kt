package ru.netology.linked.presentation.viewholder

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardEventBinding
import ru.netology.linked.domain.dto.Event
import ru.netology.linked.presentation.view.loadAuthorAvatar

class EventViewHolder(
    private val binding: CardEventBinding,
    private val onInteractionListener: OnInteractionListener<Event>,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event) {
        setContent(event)
        setupClickListeners(event)
    }

    private fun setContent(event: Event) {
        binding.apply {
            authorTextView.text = event.author
            publishedTextView.text = event.published
            postTextView.text = event.content
            likesButton.text = event.likes.toString()
            likesButton.isChecked = event.likedByMe
            eventType.text = if (event.type == Event.EventType.OFFLINE) {
                binding.root.context.getString(R.string.Offline)
            } else {
                binding.root.context.getString(R.string.Online)
            }

            if (event.authorAvatar != null) {
                avatarImageView.loadAuthorAvatar(event.authorAvatar)
            } else {
                avatarImageView.setImageResource(R.drawable.posts_avatars)
            }
            menuButton.visibility = if (event.ownerByMe) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners(event: Event) {
        binding.apply {
            likesButton.setOnClickListener { onInteractionListener.onLike(event) }
            menuButton.setOnClickListener { setupPopupMenu(it, event) }
        }
    }

    private fun setupPopupMenu(view: View, event: Event) {

        PopupMenu(view.context, view).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.remove -> {
                        onInteractionListener.onRemove(event)
                        true
                    }
                    R.id.edit -> {
                        onInteractionListener.onEdit(event)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}