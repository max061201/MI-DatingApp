package com.MI.DatingApp.viewModel.user

import android.util.Log
import androidx.compose.runtime.currentCompositionErrors
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.registieren.FirebaseIm
import com.google.firebase.database.*

class UserViewModel: ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData


    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    init {
        fetchUsers()
    }
    var momentanerUser: User = CurrentUser.getUser()!!
    val changes = mutableMapOf<String, Any>()
    var firebaseIm = FirebaseIm()

    fun like(_currentShownUser: User) {
        Log.d("Righswipe", "like")
        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        Log.d("_currentShownUser", _currentShownUser.toString())
        Log.d("momentanerUser", momentanerUser.toString())

        _currentShownUser.let { currentShownUser ->
            //hat der user den anderen schonmal geliked/ gedisliked
            if (!momentanerUser.likes.contains(currentShownUser.id)
                && !momentanerUser.dislikes.contains(currentShownUser.id) && !currentShownUser.likes.contains(momentanerUser.id) ) {

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
//                CurrentUser.setUserLocalStorage(momentanerUser) // Lokal CurrentUser aktualisieren
//                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

          //      CurrentUser.firebaseRealTimeDB.child(newUser.id).setValue(newUser)

//                currentShownUser.receivedLikes.add(momentanerUser.id)
//                changes["receivedLikes"] = currentShownUser.receivedLikes // Änderungen hinzufügen
//                firebaseIm.updateUserToDatabase(changes, currentShownUser.id) // Datenbank aktualisieren
//                changes.clear()
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
                CurrentUser.setUserLocalStorage(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

                //erstelle ein Match
                firebaseIm.createMatch(momentanerUser.id,currentShownUser.id)
            } else{
                Log.d("User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }

        }
    }
    fun dislike(_currentShownUser: User) {
        Log.d("leftswipe", "dislike")
        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        _currentShownUser.let { currentShownUser ->
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

    // FetchUsers-Funktion, die Benutzer auf der Serverseite filtert
// FetchUsers-Funktion, die Benutzer auf der Serverseite filtert
    fun fetchUsers2() {
        val currentUser = momentanerUser
        if (currentUser != null) {
            firebaseRefUsers.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = mutableListOf<User>()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (user != null && shouldShowUser(CurrentUser.getUser()!!, user)) {
                            userList.add(user)
                        }
                    }
                    _users.value = userList
                    //_users.postValue(userList) // Post changes to LiveData
                    Log.d("fetchUsers", "PostValue executed, userList size: ${userList.size}")

                  //  Log.d("fetchUsers userList", userList.toString())

                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle onCancelled if needed
                }
            })
        }
    }

    fun fetchUsers() {
        lateinit var gender1: String
        var count = 0
        currentUserLiveData.observeForever { currentUser ->
            if (currentUser != null) {
                //val query = firebaseRefUsers.orderByChild("id").startAt(currentUser.id) // das ist für wenn mann nur bestimmte anzahl bzw gholt hat das man bei einer bestimmten id weiter machen kann
                firebaseRefUsers.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userList = mutableListOf<User>()
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            // Füge Benutzer zur Liste hinzu, wenn sie den Kriterien entsprechen
                            if (user != null && shouldShowUser(currentUser, user)) {
                                userList.add(user)
                                Log.d("userList", userList.toString())
                            }
                        }
                        _users.value = userList

                        if (count <= 0){
                            // Aktualisiere die LiveData mit der gefilterten Benutzerliste
                            _users.value = userList
                            count++
                            gender1 = currentUser.gender
                            Log.d("userList count 1", userList.toString() + gender1 )

                        }
                        if (gender1.isNotEmpty() && gender1 != currentUser.gender){
                            // Aktualisiere die LiveData mit der gefilterten Benutzerliste
                            _users.value = userList
                            gender1 = currentUser.gender
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
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

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled if needed
            }
        })
    }

    // Funktion, um zu überprüfen, ob ein Benutzer angezeigt werden soll
    private fun shouldShowUser(currentUser: User, user: User): Boolean {
        Log.d("fetchUsers currentUser", currentUser.genderLookingFor.toString())

        return user.id != currentUser.id &&
                !currentUser.likes.contains(user.id) &&
                !currentUser.dislikes.contains(user.id) &&
                currentUser.genderLookingFor == user.gender

    }

}