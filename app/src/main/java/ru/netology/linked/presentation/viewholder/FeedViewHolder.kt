package ru.netology.linked.presentation.viewholder

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.view.loadAuthorAvatar
import ru.netology.linked.presentation.view.loadImageMedia

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

            post.authorAvatar?.let { }
            if (post.authorAvatar != null) {
                avatarImageView.loadAuthorAvatar(post.authorAvatar)
            }else {
                avatarImageView.setImageResource(R.drawable.posts_avatars)
            }
            menuButton.visibility = if (post.ownedByMe) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners(post: Post) {
        binding.apply {
            menuButton.setOnClickListener { setupPopupMenu(it, post) }
        }
    }

    private fun setupPopupMenu(view: View, post: Post) {

        PopupMenu(view.context, view).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.remove -> {
                        onInteractionListener.onRemove(post)
                        true
                    }
                    R.id.edit -> {
                        onInteractionListener.onEdit(post)
                        true
                    }
                    else -> false
                }
            }
        }.show()
    }
}