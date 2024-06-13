package com.MI.DatingApp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
        Log.d("Login", "Login gestartet")

    }

    fun login() {
        viewModelScope.launch {
            Log.d("Login", "Login gestartet")
            _loginState.value = LoginState.Loading
            Log.d("Login", "Status: Loading")

            kotlinx.coroutines.delay(2000) // Simuliert eine Verzögerung für den Login-Prozess

            _loginState.value = LoginState.Success
            Log.d("Login", "Status: Success")
        }
    }

}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
