package ru.netology.linked.presentation.activity

import ru.netology.linked.domain.dto.Event
import ru.netology.linked.presentation.viewholder.OnInteractionListener
import ru.netology.linked.presentation.viewmodel.AuthViewModel
import ru.netology.linked.presentation.viewmodel.MainViewModel

class EventOnInteractionListener(
    private val authViewModel: AuthViewModel,
    private val mainViewModel: MainViewModel,
) : OnInteractionListener<Event> {
    override fun onLike(event: Event) {
        if (authViewModel.authorized) {
            mainViewModel.likeEvent(event)
        } else {
            authViewModel.signIn()
        }
    }

    override fun onDetails(event: Event) {

    }

    override fun onEdit(event: Event) {
        mainViewModel.editEvent(event)
    }

    override fun onRemove(event: Event) {
        mainViewModel.removeEventById(event.id)
    }
}