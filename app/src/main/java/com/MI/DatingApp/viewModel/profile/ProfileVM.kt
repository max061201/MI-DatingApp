package com.MI.DatingApp.viewModel.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser

class ProfileVM() : ViewModel() {

    private val _user = MutableLiveData<UserEdit>().apply { value = UserEdit() }
    val user: LiveData<UserEdit> = _user
    fun setUserValue(user: UserEdit) {
        val uservalue = _user.value!!.copy(
            name = user.name,
            email = user.email,
            date = user.date,
            gander = user.gander,
            imageUrl = user.imageUrl,
            describes = user.describes
        )
        _user.value = uservalue
    }

    fun setName(name:String){
        val uservalue = _user.value!!.copy(
            name = name
        )
        _user.value = uservalue
    }
    fun setGander(gander:String){
        val uservalue = _user.value!!.copy(
            gander = gander
        )
        _user.value = uservalue
    }
    fun setDesc(describes:String){
        val uservalue = _user.value!!.copy(
            describes = describes
        )
        _user.value = uservalue
    }

    fun setEmail(email:String){
        val uservalue = _user.value!!.copy(
            email = email
        )
        _user.value = uservalue
    }

    fun setDate(date:String){
        val uservalue = _user.value!!.copy(
            date = date
        )
        _user.value = uservalue
    }

    fun setImage(image:String){
        val uservalue = _user.value!!.apply {
            this.imageUrl!!.toMutableSet().add(image)
        }
        _user.value = uservalue
    }

    fun updateDataFirebase() {
        //TODO("send data to firebase")

        Log.d("updateDataFirebase CurrentUser", CurrentUser.getUser().toString())

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