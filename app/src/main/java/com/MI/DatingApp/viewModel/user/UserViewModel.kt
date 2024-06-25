package com.MI.DatingApp.viewModel.user

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class UserViewModel : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val currentUserId =  currentUser?.uid

    init {
        fetchUsers()
    }

    fun like(user: User) {
        currentUserId?.let { id ->
            firebaseRefUsers.child(id).child("likes").child(user.id).setValue(user.id)
            firebaseRefUsers.child(user.id).child("receivedLikes").child(id).setValue(id)
        }
        updateUsers()
    }

    fun dislike(user: User) {
        currentUserId?.let { id ->
            firebaseRefUsers.child(id).child("dislikes").get().addOnSuccessListener { dataSnapshot ->
                val dislikes = dataSnapshot.getValue(object : GenericTypeIndicator<MutableList<String>>() {}) ?: mutableListOf()
                if (!dislikes.contains(user.id)) {
                    dislikes.add(user.id)
                    firebaseRefUsers.child(id).child("dislikes").setValue(dislikes)
                }
            }
        }
        updateUsers()

    }

    private fun fetchUsers() {
        firebaseRefUsers.addValueEventListener(object : ValueEventListener {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                val currentUser = snapshot.child(currentUserId ?: return).getValue(User::class.java)

                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && shouldShowUser(currentUser, user)) {
                        user.yearOfBirth = calculateAge(user.yearOfBirth).toString()
                        userList.add(user)
                    }
                }
                _users.value = userList
            }

            override fun onCancelled(error: DatabaseError) {
                // Log or handle the error
            }
        })
    }

    private fun shouldShowUser(currentUser: User, user: User): Boolean {

        return user.id != currentUser.id &&
                !currentUser.likes.contains(user.id) &&
                !currentUser.dislikes.contains(user.id)
                && currentUser.genderLookingFor == user.gender
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(birthdate: String): Int {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val birthDate = LocalDate.parse(birthdate, formatter)
        val currentDate = LocalDate.now()
        return Period.between(birthDate, currentDate).years
    }

    private fun getCurrentUserId(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    private fun updateUsers() {
        fetchUsers()
    }
}