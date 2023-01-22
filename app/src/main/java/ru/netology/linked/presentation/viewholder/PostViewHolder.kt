package ru.netology.linked.presentation.viewholder

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardPostBinding
import ru.netology.linked.domain.dto.AttachmentType
import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.view.loadAuthorAvatar
import ru.netology.linked.presentation.view.loadImageMedia

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener<Post>,
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
            likesButton.text = post.likes.toString()
            likesButton.isChecked = post.likedByMe

            if (post.authorAvatar != null) {
                avatarImageView.loadAuthorAvatar(post.authorAvatar)
            } else {
                avatarImageView.setImageResource(R.drawable.posts_avatars)
            }
            menuButton.visibility = if (post.ownedByMe) View.VISIBLE else View.GONE

            if (post.attachment == null) {
                mediaImageView.setImageResource(0)
                media.visibility = View.GONE
                mediaImageView.visibility = View.GONE
            } else {
                media.visibility = View.VISIBLE
                mediaImageView.visibility =
                    if (post.attachment.type == AttachmentType.IMAGE) View.VISIBLE else View.GONE

                when (post.attachment.type) {
                    AttachmentType.IMAGE -> {
                        mediaImageView.loadImageMedia(post.attachment.url)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupClickListeners(post: Post) {
        binding.apply {
            likesButton.setOnClickListener { onInteractionListener.onLike(post) }
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