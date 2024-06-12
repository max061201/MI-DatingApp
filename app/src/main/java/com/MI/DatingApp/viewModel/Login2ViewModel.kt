
package com.MI.DatingApp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MI.DatingApp.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Login2ViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _statusMessage = MutableStateFlow<String>("")
    val statusMessage: StateFlow<String> = _statusMessage

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }



    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

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
    // Firebase-Referenz
    private var firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("contacts")

    fun verifyData() {
        val nameValue = _name.value ?: ""
        val passwordValue = _password.value ?: ""

        if (nameValue.isEmpty()) {
            _statusMessage.value = "Name is empty"
            return
        }
        if (passwordValue.isEmpty()) {
            _statusMessage.value = "Password is empty"
            return
        }


        // Suche nach dem Kontakt mit dem angegebenen Namen
        firebaseRef.orderByChild("name").equalTo(nameValue).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Es gibt mindestens einen Kontakt mit dem angegebenen Namen
                    for (contactSnapshot in dataSnapshot.children) {
                        val contact = contactSnapshot.getValue(Contact::class.java)
                        if (contact != null && contact.password == passwordValue) {

                            // Passwort stimmt überein
                            _statusMessage.value = "Login erfolgreich"
                            Log.d("Login", "Login erfolgreich")
                            _loginState.value = LoginState.Success  // Login-State auf Success setzen
                            return
                        }
                    }
                    // Kein Kontakt mit dem passenden Passwort gefunden
                    _statusMessage.value = "Falsches Passwort"
                    Log.d("Login", "Falsches Passwort")
                    _loginState.value = LoginState.Error("Falsches Passwort") // Fehlerstate setzen

                } else {
                    // Kein Kontakt mit dem angegebenen Namen gefunden
                    _statusMessage.value = "Kein Benutzer mit diesem Namen gefunden"
                    Log.d("Login", "Kein Benutzer mit diesem Namen gefunden")
                    _loginState.value = LoginState.Error("Falsches Passwort") // Fehlerstate setzen

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Fehler beim Abrufen der Daten
                _statusMessage.value = "Fehler beim Abrufen der Daten: ${databaseError.message}"
                Log.d("Login", "Fehler beim Abrufen der Daten: ${databaseError.message}")
            }
        })
    }

}