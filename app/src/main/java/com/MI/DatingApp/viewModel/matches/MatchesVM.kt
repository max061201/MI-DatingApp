package com.MI.DatingApp.viewModel.likes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MatchesVM  : ViewModel() {

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData
    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val firebaseRefMatches: DatabaseReference = FirebaseDatabase.getInstance().getReference("Matches")

    private val _matchUsersLiveData = MutableLiveData<List<User>>()
    val matchLikesUsersLiveData: LiveData<List<User>> = _matchUsersLiveData

    init {
        getUsersMatches()
    }
    val userList = mutableListOf<User>()


    fun getUsersMatches() {
        val currentUserId = currentUserLiveData.value?.id ?: return

        val matchesRef = FirebaseDatabase.getInstance().getReference("Matches")
        val usersRef = FirebaseDatabase.getInstance().getReference("Users")

        matchesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val matchesList = mutableListOf<String>()

                for (matchSnapshot in snapshot.children) {
                    val userId1 = matchSnapshot.child("userId1").getValue(String::class.java)
                    val userId2 = matchSnapshot.child("userId2").getValue(String::class.java)

                    if (userId1 == currentUserId) {
                        userId2?.let { matchesList.add(it) }
                    } else if (userId2 == currentUserId) {
                        userId1?.let { matchesList.add(it) }
                    }
                }

                if (matchesList.isNotEmpty()) {
                    loadUsersForMatches(matchesList)
                } else {
                    _matchUsersLiveData.value = emptyList()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("getUsersMatches", "Failed to load matches", error.toException())
            }
        })
    }

    private fun loadUsersForMatches(matchesList: List<String>) {
        val usersRef = FirebaseDatabase.getInstance().getReference("Users")
        val userList = mutableListOf<User>()
        var remainingQueries = matchesList.size

        matchesList.forEach { userId ->
            usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                    remainingQueries--
                    if (remainingQueries == 0) {
                        _matchUsersLiveData.value = userList
                        Log.d("UserMatches", userList.joinToString(", ") { it.name })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    remainingQueries--
                    if (remainingQueries == 0) {
                        _matchUsersLiveData.value = userList
                    }
                    Log.e("UserMatches", "Failed to load user with ID $userId", error.toException())
                }
            })
        }
    }


}
