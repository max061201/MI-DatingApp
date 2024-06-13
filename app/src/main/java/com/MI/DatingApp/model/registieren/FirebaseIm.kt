package com.MI.DatingApp.model.registieren

import android.util.Log
import com.MI.DatingApp.viewModel.registering.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth;


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

    override fun saveUserInfo(user: User) {
        TODO("Not yet implemented")
    }
}