package com.MI.DatingApp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MI.DatingApp.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val database: DatabaseReference = Firebase.database.reference

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user: StateFlow<FirebaseUser?> = _user

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        val emailValue = email.value
        val passwordValue = password.value

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            auth.signInWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginState.value = LoginState.Success
                        fetchUser(auth.currentUser?.uid)
                    } else {
                        _loginState.value = LoginState.Error(task.exception?.message ?: "Unknown error")
                    }
                }
        }
    }

    private fun fetchUser(uid: String?) {
        uid?.let {
            database.child("users").child(uid).get().addOnSuccessListener { snapshot ->
                _user.value = snapshot?.getValue(User::class.java) as FirebaseUser?
            }.addOnFailureListener {
                _loginState.value = LoginState.Error(it.message ?: "Error fetching user data")
            }
        }
    }

    fun getUser() {
      auth.currentUser?.uid?.let { fetchUser(it) }
    }

}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
