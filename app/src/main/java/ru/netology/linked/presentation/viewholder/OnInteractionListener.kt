package ru.netology.linked.presentation.viewholder

interface OnInteractionListener<Type> {
    fun onLike(value: Type)
    fun onDetails(value: Type)
    fun onEdit(value: Type)
    fun onRemove(value: Type)
}