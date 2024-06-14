package com.MI.DatingApp.viewModel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.R
import com.MI.DatingApp.model.UserFirebase
import com.MI.DatingApp.viewModel.registering.User

class HomeVM :ViewModel() {
    private val _user = MutableLiveData<MutableList<UserFirebase>>().apply { value =
        mutableListOf( UserFirebase(
        name = "test",
        yearOfBirth = "50",
        description = "hallo i am test ",
        interests = mutableSetOf("s","f","a"),
        email = "aaa",
        gender = "male",
        id = 123L,
        lookingFor = "woman",
        photos = mutableListOf(R.drawable.heart,R.drawable.delete)
    ) )}
    val user: LiveData<MutableList<UserFirebase>> = _user

    //TODO get infor from fire base
    fun getUsers(){
        // get users from firebase and save it in _user


    }

    fun like(id:Long){
        // send to firebase new Like from user who use the app right naw

    }


}


