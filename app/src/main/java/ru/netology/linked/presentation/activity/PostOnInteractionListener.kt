package ru.netology.linked.presentation.activity

import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel

class PostOnInteractionListener(
    private val authViewModel: AuthViewModel,
    private val mainViewModel: MainViewModel,
) : OnInteractionListener<Post> {
    override fun onLike(value: Post) {
        if (authViewModel.authorized) {
            mainViewModel.likePost(value)
        } else {
            authViewModel.signIn()
        }
    }

    override fun onDetails(value: Post) {

    }

    override fun onEdit(value: Post) {
        mainViewModel.editPost(value)
    }

    override fun onRemove(value: Post) {
        mainViewModel.removePostById(value.id)
    }
}