package com.MI.DatingApp.viewModel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileVM() : ViewModel() {

    private val _userchanges = MutableLiveData<UserEdit>().apply { value = UserEdit() }
    val userchanges: LiveData<UserEdit> = _userchanges

    fun setUserValue(user: UserEdit) {
        val uservalue = _userchanges.value!!.copy(
            name = user.name,
            email = user.email,
            date = user.date,
            gander = user.gander,
            imageUrl = user.imageUrl,
            describes = user.describes
        )
        _userchanges.value = uservalue
    }

    fun setName(name:String){
        val uservalue = _userchanges.value!!.copy(
            name = name
        )
        _userchanges.value = uservalue
    }
    fun setGander(gander:String){
        val uservalue = _userchanges.value!!.copy(
            gander = gander
        )
        _userchanges.value = uservalue
    }
    fun setDesc(describes:String){
        val uservalue = _userchanges.value!!.copy(
            describes = describes
        )
        _userchanges.value = uservalue
    }

    fun setEmail(email:String){
        val uservalue = _userchanges.value!!.copy(
            email = email
        )
        _userchanges.value = uservalue
    }

    fun setDate(date:String){
        val uservalue = _userchanges.value!!.copy(
            date = date
        )
        _userchanges.value = uservalue
    }

    fun setImage(image:String){
        val uservalue = _userchanges.value!!.apply {
            this.imageUrl!!.toMutableSet().add(image)
        }
        _userchanges.value = uservalue
    }
    private var firebaseRefRealTime: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    fun updateDataFirebase() {
        val currentUser = CurrentUser.getTestUser()
        val changes = mutableMapOf<String, Any>()

        _userchanges.value?.let { userChanges ->
            if (currentUser.name != userChanges.name) {
                changes["name"] = userChanges.name
            }
            if (currentUser.email != userChanges.email) {
                changes["email"] = userChanges.email
            }
            if (currentUser.yearOfBirth != userChanges.date) {
                changes["yearOfBirth"] = userChanges.date
            }
            if (currentUser.gender != userChanges.gander) {
                changes["gender"] = userChanges.gander
            }
            if (currentUser.description != userChanges.describes) {
                changes["description"] = userChanges.describes
            }
//            if (currentUser.imageUrl != userChanges.imageUrl?.toList()) {
//                changes["imageUrl"] = userChanges.imageUrl?.toList() ?: emptyList<String>()
//            }
        }

        if (changes.isEmpty()) {
            Log.d("UpdateDataFirebase", "No changes detected.")
        } else {
            Log.d("UpdateDataFirebase", "Changes detected: $changes")

            // Update the Firebase Realtime Database with the changes
            val userId = currentUser.id  // Assuming you have an ID field in your User model
            firebaseRefRealTime.child(userId).updateChildren(changes)
                .addOnCompleteListener {
                    Log.d("Firebase", "User updated successfully")
                }
                .addOnFailureListener {
                    Log.d("Firebase", "Error updating user: ${it.message}")
                }
        }
    }

    fun deleteAccount() {
       // TODO("delete Account firebase")
    }

}

data class UserEdit(
    var name: String = "dsaasd",
    var email: String = "asdasd",
    var date: String = "",
    var gander: String = "",
    var imageUrl: MutableSet<String>? = mutableSetOf(),  // Ändere das hier
    var describes: String = "",
)