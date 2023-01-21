package ru.netology.linked.presentation.viewmodel

sealed interface FeedModelState{
    object Idle: FeedModelState
    object Loading: FeedModelState
    object Refresh: FeedModelState
    object Error: FeedModelState
}
