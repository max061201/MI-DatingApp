package com.MI.DatingApp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.Contact
import com.MI.DatingApp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    data class User2(
        var name: String = "",
        var email: String = "",
        var password: String = "",
        var confirmedPassword: String = "",
        var date: String = "",
        var gander: String = "",
        //var image: Bitmap? = null,
        var ganderLookingFor: String = "",
        var describes: String = "",
        // var interest: MutableSet<String> = mutableSetOf(),
    )
    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    fun saveRegData() {
        var user = User2("max","max@gmail.com","123456","123456","01.06.2021","Male","Woman","Der King")


        // Erstellen eines Kontaktobjekts
        val contactId = firebaseRefUsers.push().key ?: ""
        //val contact = Contact(contactId, nameValue, passwordValue)

        // Daten in Firebase speichern
        firebaseRefUsers.child(contactId).setValue(user)
            .addOnCompleteListener {
                _statusMessage.value = "Daten erfolgreich gespeichert."
            }
            .addOnFailureListener {
                _statusMessage.value = "Fehler beim Speichern der Daten: ${it.message}"
            }
    }

    fun getAllUsersData() {
        firebaseRefUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                Log.d("Login", userList.toString())

                //_users.value = userList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                _statusMessage.value = "Fehler beim Abrufen der Daten: ${databaseError.message}"
            }
        })
    }
}
