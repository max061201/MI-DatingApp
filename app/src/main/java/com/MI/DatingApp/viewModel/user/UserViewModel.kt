package com.MI.DatingApp.viewModel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.User
import com.google.firebase.database.*

class UserViewModel: ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users


    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    init {
        fetchUsers()
    }
    fun like(user: User) {
        Log.d("Righswipe", "like $user")
    }
    fun dislike(user: User) {
        Log.d("Righswipe", "like")
    }
    private fun fetchUsers() {
        firebaseRefUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null) {
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


}
