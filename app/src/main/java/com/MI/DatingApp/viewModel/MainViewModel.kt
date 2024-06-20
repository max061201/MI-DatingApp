package com.MI.DatingApp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.Contact
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.registieren.FirebaseIm
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

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _currentUserIndex = MutableLiveData(0)
    val currentUserIndex: LiveData<Int> get() = _currentUserIndex

    private val _currentShownUser = MutableLiveData<User?>()
    val currentShownUser: LiveData<User?> get() = _currentShownUser



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
    fun showNextUser() {
        val usersList = _users.value ?: return
        val nextIndex = (_currentUserIndex.value ?: 0) + 1
        if (nextIndex < usersList.size) {
            _currentUserIndex.value = nextIndex
            _currentShownUser.value = usersList[nextIndex]
        }
    }

    fun showPreviousUser() {
        val prevIndex = (_currentUserIndex.value ?: 0) - 1
        if (prevIndex >= 0) {
            _currentUserIndex.value = prevIndex
            _currentShownUser.value = _users.value?.get(prevIndex)
        }
    }
    var momentanerUser = CurrentUser.getTestUser()
    var firebaseIm = FirebaseIm()
    val changes = mutableMapOf<String, Any>()

    fun Dislike() {

        Log.d("Dislike", "Dislike")

        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        _currentShownUser.value?.let { currentShownUser ->
            if (!momentanerUser.likes.contains(currentShownUser.id) && !momentanerUser.dislikes.contains(currentShownUser.id)) {                momentanerUser.dislikes.add(currentShownUser.id)
                changes["dislikes"] = momentanerUser.dislikes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                Log.d("changes", changes.toString())
                Log.d("momentanerUser", momentanerUser.toString())
               // CurrentUser.setUser(momentanerUser) updated current userdata
            }else{
                Log.d("User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }
        }
    }
    fun Like() {

        Log.d("Like", "Like")
        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        Log.d("_currentShownUser", _currentShownUser.toString())
        Log.d("momentanerUser", momentanerUser.toString())

        _currentShownUser.value?.let { currentShownUser ->
            //hat der user den anderen schonmal geliked/ gedisliked
            if (!momentanerUser.likes.contains(currentShownUser.id) && !momentanerUser.dislikes.contains(currentShownUser.id)   ) {

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

                currentShownUser.receivedLikes.add(momentanerUser.id)
                changes["receivedLikes"] = currentShownUser.receivedLikes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, currentShownUser.id) // Datenbank aktualisieren
                changes.clear()
                Log.d("currentShownUser receivedLikes", currentShownUser.receivedLikes.toString())


                // CurrentUser.setUser(momentanerUser) updated current userdata
            }

            // hat einer der user denn anderen schonmal geliked?
            //vlt irgenwie pop up mit Match gefunden oder so?
            else if (currentShownUser.likes.contains(momentanerUser.id)){
                Log.d("MATCH", "es gibt ein mensch zwischen ${momentanerUser.name} und ${currentShownUser.name} ")

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

                //erstelle ein Match
                firebaseIm.createMatch(momentanerUser.id,currentShownUser.id)
            }
            else{
                Log.d("User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }

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

                _users.value = userList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                _statusMessage.value = "Fehler beim Abrufen der Daten: ${databaseError.message}"
            }
        })
    }

}
