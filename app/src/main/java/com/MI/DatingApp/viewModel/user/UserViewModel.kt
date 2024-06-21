package com.MI.DatingApp.viewModel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.google.firebase.database.*

class UserViewModel: ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData


    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    init {
        fetchUsers()
    }
    fun like(user: User) {
        Log.d("Righswipe", "like")
        Log.d("_currentShownUser", user.toString())
        Log.d("momentanerUser", currentUserLiveData.value.toString())
    }
    fun dislike(user: User) {
        Log.d("leftswipe", "dislike")
    }

    // FetchUsers-Funktion, die Benutzer auf der Serverseite filtert
    private fun fetchUsers() {
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
                            }
                        }
                        // Aktualisiere die LiveData mit der gefilterten Benutzerliste
                        _users.value = userList
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
        }
    }

    // Funktion, um zu überprüfen, ob ein Benutzer angezeigt werden soll
    private fun shouldShowUser(currentUser: User, user: User): Boolean {
        return user.id != currentUser.id &&
                !currentUser.likes.contains(user.id) &&
                !currentUser.dislikes.contains(user.id)
    }

}