package com.MI.DatingApp.viewModel.likes

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikesVM  : ViewModel() {

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData
    private val firebaseRefUsers = FirebaseDatabase.getInstance().getReference("users")
    private val _receivedLikesUsersLiveData = MutableLiveData<List<User>>()
    val receivedLikesUsersLiveData: LiveData<List<User>> = _receivedLikesUsersLiveData
    init {
        getReceivedLikes()
    }

    fun getReceivedLikes() {
        val receivedLikesIds = currentUserLiveData.value?.receivedLikes ?: return

        val userList = mutableListOf<User>()
        var remainingQueries = receivedLikesIds.size



        receivedLikesIds.forEach { userId ->
            firebaseRefUsers.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                    remainingQueries--
                    if (remainingQueries == 0) {
                        _receivedLikesUsersLiveData.value = userList
                        Log.d("ReceivedLikesUsers", userList.joinToString(", ") { it.name })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    remainingQueries--
                    if (remainingQueries == 0) {
                        _receivedLikesUsersLiveData.value = userList
                    }
                    Log.e("ReceivedLikesUsers", "Failed to load user with ID $userId", error.toException())
                }
            })
        }
    }
}
