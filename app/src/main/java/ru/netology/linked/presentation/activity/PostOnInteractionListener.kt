package ru.netology.linked.presentation.activity

import ru.netology.linked.domain.dto.Post
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel

class PostOnInteractionListener(
    private val authViewModel: AuthViewModel,
    private val mainViewModel: MainViewModel,
) : OnInteractionListener<Post> {
    override fun onLike(post: Post) {
        if (authViewModel.authorized) {
            mainViewModel.likePost(post)
        } else {
            authViewModel.signIn()
        }
    }

    override fun onDetails(post: Post) {

    }

    override fun onEdit(post: Post) {
        mainViewModel.editPost(post)
    }

    override fun onRemove(post: Post) {
        mainViewModel.removePostById(post.id)
    }
}