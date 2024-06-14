package com.MI.DatingApp.model.registieren

import android.util.Log
import com.MI.DatingApp.model.Contact
import com.MI.DatingApp.viewModel.registering.User
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

    fun saveUserInRealtime(user: User) {

        // Erstellen eines Kontaktobjekts
        val contactId = firebaseRef.push().key ?: ""

        //user.image = null

        Log.d("_user", user.toString())
        // Daten in Firebase speichern
        firebaseRef.child(contactId).setValue(user)  // Speichere _user.value direkt
            .addOnCompleteListener {
                //_statusMessage.value = "Daten erfolgreich gespeichert."
            }
            .addOnFailureListener {
                // _statusMessage.value = "Fehler beim Speichern der Daten: ${it.message}"
            }

    }

    override fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }
}