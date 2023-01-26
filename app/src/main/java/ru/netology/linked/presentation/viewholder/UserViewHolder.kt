package ru.netology.linked.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import ru.netology.linked.R
import ru.netology.linked.databinding.CardUserBinding
import ru.netology.linked.domain.dto.User
import ru.netology.linked.presentation.view.loadAuthorAvatar

class UserViewHolder(
    private val binding: CardUserBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        setContent(user)
    }

    private fun setContent(user: User) {
        binding.apply {
            userLoginTextView.text = user.login
            userNameTextView.text = user.name

            if (user.avatar != null) {
                avatarUserImageView.loadAuthorAvatar(user.avatar)
            } else {
                avatarUserImageView.setImageResource(R.drawable.posts_avatars)
            }
        }
    }
}