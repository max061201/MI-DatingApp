package com.MI.DatingApp.model.registieren

import com.MI.DatingApp.viewModel.registering.User

interface RegistierenSaveData {


    fun saveUserAuth(user :User)
    fun saveUserInfo(user :User)
}