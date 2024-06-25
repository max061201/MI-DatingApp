package com.MI.DatingApp.viewModel.registering


import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.registieren.FirebaseIm
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.MI.DatingApp.model.User


class RegisteringVM : ViewModel() {

    private val _user = MutableLiveData<User>().apply { value = User() }
    val user: LiveData<User> = _user

    private val _errorField = MutableLiveData<MutableList<Error>>().apply { value = emptyList<Error>().toMutableList() }
    val errorField: LiveData<MutableList<Error>> = _errorField

    var firebaseIm = FirebaseIm()





    fun setId(id: String) {
        val updatedUser = _user.value?.copy(id = id)
        _user.value = updatedUser
    }

    fun setName(name: String) {
        val updatedUser = _user.value?.copy(name = name)
        _user.value = updatedUser
    }

    fun setEmail(email: String) {
        val updatedUser = _user.value?.copy(email = email)
        _user.value = updatedUser
    }

    fun setPassword(password: String) {
        val updatedUser = _user.value?.copy(password = password)
        _user.value = updatedUser
    }

    fun setGander(gender: String) {
        val updatedUser = _user.value?.copy(gender = gender)
        _user.value = updatedUser
    }

    fun setGanderLookingFor(gender: String) {
        val updatedUser = _user.value?.copy(genderLookingFor = gender)
        _user.value = updatedUser
    }

    fun describe(describe: String) {
        val updatedUser = _user.value?.copy(description = describe)
        _user.value = updatedUser
    }

    fun setConfirmedPassword(confirmedPassword: String) {
        val updatedUser = _user.value?.copy(confirmedPassword = confirmedPassword)
        _user.value = updatedUser
    }


    fun setImagePath(imagePath: String) {
        val updatedUser = _user.value?.apply {
            imageUrls?.add(imagePath)
        }
        _user.value = updatedUser
    }


    fun setInterestes(interest: String) {
        _user.value?.let { currentUser ->
            val updatedInterests = currentUser.interest.toMutableList().apply {
                if (this.contains(interest)) {
                    this.remove(interest)
                } else {
                    add(interest)
                }
            }

            // Create a new User object with updated interests
            val updatedUser = currentUser.copy(interest = updatedInterests)

            // Update the state
            _user.value = updatedUser
        }
    }

    fun setError(error: MutableList<Error>) {
        _errorField.value = error
    }

    fun setDate(date: String) {
        val updatedUser = _user.value?.copy(yearOfBirth = date)
        _user.value = updatedUser
    }

    fun checkErrorForFirstPage(): Boolean {

        val check = _user.value!!.emptyFields().isEmpty()
        val mutableListOfError: MutableList<Error> = emptyList<Error>().toMutableList()
        if (!check) {
            _user.value!!.emptyFields().forEach {
                findErrorTextAndRemove(mutableListOfError, it)
                mutableListOfError.add(Error(errorType = it, error = true))
            }
            setError(mutableListOfError)
            return false
        } else {
            if (!_user.value!!.matchPassword()) {
                setError(
                    mutableListOf(
                        Error(
                            errorType = "passowrd not match or less than 6 char",
                            error = true
                        )
                    )
                )
                return false
            } else {
                setError(
                    emptyList<Error>().toMutableList()
                )

            }
            return true
        }
    }

    fun checkErrorForSecondPage(): Boolean {
        val check = _user.value!!.yearOfBirth.isNotEmpty() && _user.value!!.gender.isNotEmpty()
        if (!check) {
            setError(mutableListOf(Error(errorType = "check Date or Gander", error = true)))
            return false
        }
        if(_user.value!!.imageUrls!!.isEmpty()){
            setError(mutableListOf(Error(errorType = " profile picture", error = true)))
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDate(date: String) {
        // TODO check if date in future
        //  return (Date.from(Instant.now()) > DateTimeFormatter.ofPattern())
    }

    private var firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private var storageRef: StorageReference = FirebaseStorage.getInstance().getReference("UsersImages")

    fun saveUserInFirebaseAuth() {
        val user = _user.value!!
        val imageUris = user.imageUrls?.map { Uri.parse(it) } ?: emptyList()

        val contactId = firebaseRef.push().key ?: ""

        setId(contactId)
        if (imageUris.isNotEmpty()) {
            uploadImagesAndSaveUser(imageUris, contactId)
        } else {
            saveUserToDatabase(null, contactId)
        }

        firebaseIm.saveUserAuth(user)
    }

    private fun uploadImagesAndSaveUser(imageUris: List<Uri>, contactId: String) {
        val uploadedImageUrls = mutableListOf<String>()
        val timestamp = System.currentTimeMillis() // Zeitstempel hinzufügen

        imageUris.forEach { uri ->
            val imageRef = storageRef.child("images/$contactId/${timestamp}_${uri.lastPathSegment}") // Zeitstempel vor dem Dateinamen
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnFailureListener {
                Log.d("Firebase", "Image upload failed: ${it.message}")
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                    uploadedImageUrls.add(downloadUri.toString())
                    if (uploadedImageUrls.size == imageUris.size) {
                        saveUserToDatabase(uploadedImageUrls, contactId)
                    }
                }
            }
        }
    }

    private fun saveUserToDatabase(imageUrls: List<String>?, contactId: String) {
        val user = _user.value!!
        val userWithImages = user.copy(imageUrls = imageUrls?.toMutableList())

        firebaseRef.child(contactId).setValue(userWithImages)
            .addOnCompleteListener {
                Log.d("Firebase", "User saved successfully")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Error saving user: ${it.message}")
            }
    }

}

private fun findErrorTextAndRemove(mutableList: MutableList<Error>, error: String) {
    mutableList.remove(mutableList.find { it.errorType == error })
}



data class Error(
    var errorType: String = "",
    var error: Boolean
)

fun Date.formatAndToString(): String {

    return SimpleDateFormat("dd.MM.yyy", Locale.getDefault()).format(this)
}

