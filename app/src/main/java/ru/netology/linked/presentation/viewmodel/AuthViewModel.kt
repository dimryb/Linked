package ru.netology.linked.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.linked.data.error.ApiError
import ru.netology.linked.domain.AuthRepository
import ru.netology.linked.domain.dto.Authentication
import ru.netology.linked.domain.dto.Token
import ru.netology.linked.presentation.auth.AppAuth
import ru.netology.linked.presentation.auth.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val appAuth: AppAuth,
) : ViewModel() {

    val dataAuth: LiveData<AuthState> = appAuth
        .authStateFlow
        .asLiveData(Dispatchers.Default)

    val authorized: Boolean
        get() = appAuth.authStateFlow.value.token != null

    private val _token = MutableLiveData<Token>()
    val token: LiveData<Token>
        get() = _token

    private val _loginError = MutableLiveData<Unit>()
    val loginError: LiveData<Unit>
        get() = _loginError

    private val _signUpSignal = MutableLiveData<Unit>()
    val signUpSignal: LiveData<Unit>
        get() = _signUpSignal

    private val _signInSignal = MutableLiveData<Unit>()
    val signInSignal: LiveData<Unit>
        get() = _signInSignal

    private val _registerErrorSignal = MutableLiveData<AuthError>()
    val registerErrorSignal: LiveData<AuthError>
        get() = _registerErrorSignal

    fun signIn() {
        _signInSignal.value = Unit
    }

    fun signUp() {
        _signUpSignal.value = Unit
    }

    fun signOut() {
        appAuth.removeAuth()
    }

    private fun registrationError(
        type: AuthErrorType = AuthErrorType.UNKNOWN,
        message: String? = null
    ) {
        _registerErrorSignal.value = AuthError(type, message)
    }

    fun registration(
        login: String,
        pass: String,
        repeatPass: String,
        name: String,
        file: String? = null
    ) {
        if (name.isBlank()) {
            registrationError(AuthErrorType.NAME_IS_BLANK)
            return
        }
        if (login.isBlank()) {
            registrationError(AuthErrorType.LOGIN_IS_BLANK)
            return
        }
        if (pass.isBlank()) {
            registrationError(AuthErrorType.PASSWORD_IS_BLANK)
            return
        }
        if (pass != repeatPass) {
            registrationError(AuthErrorType.PASSWORDS_DONT_MATCH)
            return
        }
        viewModelScope.launch {
            try {
                _token.value = repository.registration(login, pass, name, file)
            } catch (e: ApiError) {
                registrationError(AuthErrorType.API_ERROR, e.message)
            } catch (e: Exception) {
                registrationError()
            }
        }
    }

    fun authentication(login: String, pass: String) {
        viewModelScope.launch {
            try {
                _token.value = repository.authentication(Authentication(login, pass))
            } catch (e: Exception) {
                _loginError.value = Unit
            }
        }
    }
}