package com.MI.DatingApp.model.registieren

import android.util.Log
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.Match
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
    private var firebaseRefRealTimeMatch :DatabaseReference = FirebaseDatabase.getInstance().getReference("Matches")

    val currentUser = CurrentUser.getTestUser()


    fun updateUserToDatabase(changes: MutableMap<String, Any>, id: String) {
        firebaseRefRealTime.child(id).updateChildren(changes)
            .addOnCompleteListener {
                Log.d("Firebase", "User updated successfully")

            }
            .addOnFailureListener {
                Log.d("Firebase", "Error updating user: ${it.message}")
            }
    }

    fun createMatch(userId1: String, userId2: String) {
        val match = Match("", userId1, userId2) // Erstellen Sie das Match-Objekt mit leerer ID
        val newMatchRef = firebaseRefRealTimeMatch.push() // Erzeugen Sie eine neue Referenz mit automatisch generierter ID
        match.id = newMatchRef.key ?: "" // Setzen Sie die ID des Matches auf die automatisch generierte ID

        newMatchRef.setValue(match)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Match created successfully with ID: ${match.id}")
                } else {
                    Log.d("Firebase", "Error creating match: ${task.exception?.message}")
                }
            }
    }




    override fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }

}