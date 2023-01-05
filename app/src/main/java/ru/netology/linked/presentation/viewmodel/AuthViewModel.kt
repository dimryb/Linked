package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.netology.linked.R
import ru.netology.linked.domain.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){
    val authorized: Boolean = false

    private val _signUpSignal = MutableLiveData<Unit>()
    val signUpSignal: LiveData<Unit>
        get() = _signUpSignal

    private val _registerErrorSignal = MutableLiveData<RegistrationError>()
    val registerErrorSignal: LiveData<RegistrationError>
        get() = _registerErrorSignal

    fun signIn() {
        TODO("Not yet implemented")
    }

    fun signUp() {
        _signUpSignal.value = Unit
    }

    fun signOut() {
        TODO("Not yet implemented")
    }

    enum class RegistrationError {
        UNKNOWN,
        PASSWORDS_DONT_MATCH,
        NAME_IS_BLANK,
        LOGIN_IS_BLANK,
        PASSWORD_IS_BLANK,
    }

    private fun registrationError(error: RegistrationError = RegistrationError.UNKNOWN){
        _registerErrorSignal.value = error
    }

    fun registration(login: String, pass: String, repeatPass: String, name: String, file: String? = null) {
        if (name.isBlank()){
            registrationError(RegistrationError.NAME_IS_BLANK)
            return
        }
        if (login.isBlank()){
            registrationError(RegistrationError.LOGIN_IS_BLANK)
            return
        }
        if (pass.isBlank()){
            registrationError(RegistrationError.PASSWORD_IS_BLANK)
            return
        }
        if (pass != repeatPass) {
            registrationError(RegistrationError.PASSWORDS_DONT_MATCH)
            return
        }
        viewModelScope.launch {
            try {
                repository.registration(login, pass, name, file)
            } catch (e: Exception) {
                registrationError()
            }
        }
    }

}