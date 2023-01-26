package ru.netology.linked.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.netology.linked.databinding.CardUserBinding
import ru.netology.linked.domain.dto.User
import ru.netology.linked.presentation.viewholder.UserViewHolder

class UsersAdapter() : ListAdapter<User, UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = CardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}