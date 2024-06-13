package com.MI.DatingApp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.Contact
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainViewModel : ViewModel() {

    private val _number = MutableLiveData(0)
    val number: LiveData<Int> get() = _number

    private val _text = MutableLiveData("")
    val text: LiveData<String> get() = _text

    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _password = MutableLiveData("")
    val password: LiveData<String> get() = _password

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage

    fun incCount(){
        _number.value = _number.value?.plus(1)
    }

    fun onTextChanged(newText: String) {
        _text.value = newText
    }
    // Funktionen zum Aktualisieren des Namens und des Passworts
    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

     private var firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("contacts")


    // Funktion zum Speichern der Daten in Firebase
    fun saveData() {
        val nameValue = _name.value ?: ""
        val passwordValue = _password.value ?: ""

        if (nameValue.isEmpty()) {
            // Handle empty name
            return
        }
        if (passwordValue.isEmpty()) {
            // Handle empty password
            return
        }

        // Erstellen eines Kontaktobjekts
        val contactId = firebaseRef.push().key ?: ""
        val contact = Contact(contactId, nameValue, passwordValue)

        // Daten in Firebase speichern
        firebaseRef.child(contactId).setValue(contact)
            .addOnCompleteListener {
                _statusMessage.value = "Daten erfolgreich gespeichert."
            }
            .addOnFailureListener {
                _statusMessage.value = "Fehler beim Speichern der Daten: ${it.message}"
            }
    }
}