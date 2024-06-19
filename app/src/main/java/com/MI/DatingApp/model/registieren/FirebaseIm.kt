package com.MI.DatingApp.model.registieren

import android.util.Log
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FirebaseIm : RegistierenSaveData {
    val firebaseInstance = FirebaseAuth.getInstance()

    override fun saveUserAuth(user: User) {

        firebaseInstance.createUserWithEmailAndPassword(user.email,user.password)
            .addOnCompleteListener(OnCompleteListener {
                if(it.isSuccessful){
                    Log.i("firebaseTest", "createUserWithEmail:success")

                }else{
                    Log.i("firebaseTest", "createUserWithEmail:failure",it.exception)
                }
            })

    }
    private var firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private var firebaseRefRealTime: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    val currentUser = CurrentUser.getTestUser()


    fun updateUserToDatabase(changes: MutableMap<String, Any>) {
        val userId = currentUser.id
        firebaseRefRealTime.child(userId).updateChildren(changes)
            .addOnCompleteListener {
                Log.d("Firebase", "User updated successfully")

            }
            .addOnFailureListener {
                Log.d("Firebase", "Error updating user: ${it.message}")
            }
    }

    override fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }

}