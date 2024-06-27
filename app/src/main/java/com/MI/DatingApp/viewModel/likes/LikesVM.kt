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

class LikesVM  : ViewModel() {

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData
    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    private val _receivedLikesUsersLiveData = MutableLiveData<List<User>>()
    val receivedLikesUsersLiveData: LiveData<List<User>> = _receivedLikesUsersLiveData
    /**
    Call getReceivedLikes when viewmodel called
     */
    init {
        getReceivedLikes()
    }
    /**
    Get all Users that liked Current user to show his received likes for the realtime DB
     */
    fun getReceivedLikes() {
        val receivedLikesIds = currentUserLiveData.value?.receivedLikes ?: return

        // Entferne null-Werte aus der Liste
        val nonNullReceivedLikesIds = receivedLikesIds.filterNotNull()

        val userList = mutableListOf<User>()
        var remainingQueries = nonNullReceivedLikesIds.size

        nonNullReceivedLikesIds.forEach { userId ->
            firebaseRefUsers.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                //    Log.d("userListuserList1", userList.toString())

                    if (user != null) {
                        userList.add(user)
                    //    Log.d("userListuserList2", userList.toString())

                    }
                    remainingQueries--
                    if (remainingQueries == 0) {
                        _receivedLikesUsersLiveData.value = userList
                    //    Log.d("ReceivedLikesUsers", userList.joinToString(", ") { it.name })
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
