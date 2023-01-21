package ru.netology.linked.presentation.viewmodel

data class MenuState(
    val checked: MenuStateChecked = MenuStateChecked.HOME
)

enum class MenuStateChecked {
    HOME,
    USERS,
    EVENTS,
}