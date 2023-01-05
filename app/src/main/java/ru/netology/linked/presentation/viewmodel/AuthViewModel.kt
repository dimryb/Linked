package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.netology.linked.domain.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){
    val authorized: Boolean = false

    fun signIn() {
        TODO("Not yet implemented")
    }

    fun signUp() {
        TODO("Not yet implemented")
    }

    fun signOut() {
        TODO("Not yet implemented")
    }

}