package ru.netology.linked.presentation.activity

import ru.netology.linked.domain.dto.Event
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel

class EventOnInteractionListener(
    private val authViewModel: AuthViewModel,
    private val mainViewModel: MainViewModel,
) : OnInteractionListener<Event> {
    override fun onLike(value: Event) {
        if (authViewModel.authorized) {
            mainViewModel.likeEvent(value)
        } else {
            authViewModel.signIn()
        }
    }

    override fun onDetails(value: Event) {

    }

    override fun onEdit(value: Event) {
        mainViewModel.editEvent(value)
    }

    override fun onRemove(value: Event) {
        mainViewModel.removeEventById(value.id)
    }
}