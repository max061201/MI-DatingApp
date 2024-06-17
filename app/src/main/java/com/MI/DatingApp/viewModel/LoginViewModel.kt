package com.MI.DatingApp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private var firebaseRealTimeDB: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


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
                        fetchUserByEmail(emailValue)

                      //  _loginState.value = LoginState.Success

                    } else {
                        _loginState.value = LoginState.Error(task.exception?.message ?: "Unknown error")
                    }
                }
        }
    }

    private fun fetchUserByEmail(emailValue: String) {
        firebaseRealTimeDB.orderByChild("email").equalTo(emailValue).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    handleSnapshot(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    _loginState.value = LoginState.Error(error.message ?: "Error fetching user data")
                }
            }
        )
    }

    private fun handleSnapshot(snapshot: DataSnapshot) {
        if (snapshot.exists()) {
            var foundUser: User? = null
            for (userSnapshot in snapshot.children) {
                val user = userSnapshot.getValue(User::class.java)
                if (user != null) {
                    foundUser = user
                    break
                }
            }
            if (foundUser != null) {
                CurrentUser.setUser(foundUser)
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("User data not found")
            }
        } else {
            _loginState.value = LoginState.Error("User not found")
        }
        Log.d("fetchUser", _user.value.toString())
    }


    fun getUser() {
        auth.currentUser?.email?.let { fetchUserByEmail(it) }
    }
}

sealed class LoginState {
    data object Idle : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
