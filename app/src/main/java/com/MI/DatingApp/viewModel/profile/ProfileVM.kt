package com.MI.DatingApp.viewModel.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.registieren.FirebaseIm
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileVM : ViewModel() {

    private val _userchanges = MutableLiveData<User>().apply { value = User() }
    val userchanges: LiveData<User> = _userchanges

    val currentUser = CurrentUser.getTestUser()
    var deleteImages: MutableList<String> = mutableListOf()
    fun setUserValue(user: User) {
        val uservalue = _userchanges.value!!.copy(
            id = user.id,
            name = user.name,
            email = user.email,
            yearOfBirth = user.yearOfBirth,
            gender = user.gender,
            imageUrls = user.imageUrls,
            description = user.description,
            interest = user.interest,
            likes = user.likes,
            dislikes = user.dislikes
        )
        _userchanges.value = uservalue
    }

    fun setName(name: String) {
        val uservalue = _userchanges.value!!.copy(
            name = name
        )
        _userchanges.value = uservalue
    }

    fun setGender(gender: String) {
        val uservalue = _userchanges.value!!.copy(
            gender = gender
        )
        _userchanges.value = uservalue
    }

    fun setDesc(description: String) {
        val uservalue = _userchanges.value!!.copy(
            description = description
        )
        _userchanges.value = uservalue
    }

    fun setEmail(email: String) {
        val uservalue = _userchanges.value!!.copy(
            email = email
        )
        _userchanges.value = uservalue
    }

    fun setDate(yearOfBirth: String) {
        val uservalue = _userchanges.value!!.copy(
            yearOfBirth = yearOfBirth
        )
        _userchanges.value = uservalue
    }

    fun setImage(imageUri: String, imageToDelete: String = "", index: Int) {
        val uservalue = _userchanges.value!!
        val newImages = uservalue.imageUrls!!.toMutableList().apply {
            if (indexOf(imageToDelete) == -1) {
                add(imageUri)
            } else {

                removeAt(index)
                add(index, imageUri)
                deleteImages.add(imageToDelete)


            }

        }
        _userchanges.value = uservalue.copy(imageUrls = newImages)


    }

    //Set für welches images würde bearbeitet

    private var firebaseRefRealTime: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Users")

    // private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storageRef: StorageReference =
        FirebaseStorage.getInstance().getReference("UsersImages")
    val changes = mutableMapOf<String, Any>()
    var firebaseIm = FirebaseIm()


    // UpdateUSer in DB
    fun updateDataFirebase() {

        _userchanges.value?.let { userChanges ->
            if (currentUser.name != userChanges.name) {
                changes["name"] = userChanges.name
            }
            if (currentUser.email != userChanges.email) {
                changes["email"] = userChanges.email
            }
            if (currentUser.yearOfBirth != userChanges.yearOfBirth) {
                changes["yearOfBirth"] = userChanges.yearOfBirth
            }
            if (currentUser.gender != userChanges.gender) {
                changes["gender"] = userChanges.gender
            }
            if (currentUser.description != userChanges.description) {
                changes["description"] = userChanges.description
            }
            if (currentUser.imageUrls != userChanges.imageUrls?.toList()) {
                changes["imageUrls"] = userChanges.imageUrls?.toList() ?: emptyList<String>()
            }
        }

        if (changes.isEmpty()) {
            Log.d("UpdateDataFirebase", "No changes detected.")
        } else {
            Log.d("UpdateDataFirebase", "Changes detected: $changes")
            if (changes.containsKey("imageUrls")) {
                deleteSameImages()
                val user = _userchanges.value!!
                val imageUris = user.imageUrls?.map { Uri.parse(it) } ?: emptyList()

                uploadImagesAndUpdateUser(imageUris)
                //updateUserToDatabase()

            } else {
                firebaseIm.updateUserToDatabase(changes)
            }
        }
    }

    private fun deleteImagesFromFirebase() {
        currentUser.imageUrls = currentUser.imageUrls?.apply {
            removeAll(deleteImages)
        }
        deleteImages.forEach {
            Log.i("deleteImages", "deleteImagesFromFirebase: " + it)
            val imageRef =
                storageRef.child(
                    Uri.parse(it).lastPathSegment!!.replace(
                        "UsersImages/",
                        ""
                    )
                ) // Zeitstempel vor dem Dateinamen

            imageRef.delete().addOnSuccessListener {
                Log.d("successdelete", "Image deleted successfully")

            }.addOnFailureListener {
                Log.d("failddelete", "Image deleted successfully+$it")
            }
        }

    }

    private fun uploadImagesAndUpdateUser(imageUris: List<Uri>) {
        val contactId = currentUser.id
        val timestamp = System.currentTimeMillis() // Zeitstempel hinzufügen
        CoroutineScope(Dispatchers.IO).launch {
            val uploadedImageUrls = mutableListOf<String>()
            imageUris.forEach { uri ->
                //   val imageRef = storageRef.child("images/$contactId/${uri.lastPathSegment}")
                val imageRef =
                    storageRef.child("images/$contactId/${timestamp}_${uri.lastPathSegment}") // Zeitstempel vor dem Dateinamen

                val uploadTask = imageRef.putFile(uri)
                uploadTask.addOnFailureListener {
                    Log.d("Firebase", "Image upload failed: ${it.message}")
                }.addOnSuccessListener { taskSnapshot ->

                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                        uploadedImageUrls.add(downloadUri.toString())
                        if (uploadedImageUrls.size == imageUris.size) {
                            updateUserImgDatabase(uploadedImageUrls, contactId)
                        }
                    }
                    deleteImagesFromFirebase()
                }
            }
        }

    }

    private fun updateUserImgDatabase(imageUrls: List<String>, contactId: String) {
        val currentImageUrls = currentUser.imageUrls?.toMutableList() ?: mutableListOf()
        currentImageUrls.addAll(imageUrls)

        val updates = mutableMapOf<String, Any>()
        updates["imageUrls"] = currentImageUrls

        firebaseRefRealTime.child(contactId).updateChildren(updates)
            .addOnCompleteListener {
                Log.d("Firebase", "User imageUrls updated successfully")
            }
            .addOnFailureListener {
                Log.d("Firebase", "Error updating user imageUrls: ${it.message}")
            }
    }


    //    private fun updateUserToDatabase() {
//        val userId = currentUser.id
//        firebaseRefRealTime.child(userId).updateChildren(changes)
//            .addOnCompleteListener {
//                Log.d("Firebase", "User updated successfully")
//
//            }
//            .addOnFailureListener {
//                Log.d("Firebase", "Error updating user: ${it.message}")
//            }
//    }
    fun deleteSameImages() {
        val currentImageUrls = currentUser.imageUrls?.toMutableList()
        val newImageUrls = _userchanges.value?.imageUrls?.toMutableList()
        Log.d("Vor der For", _userchanges.value?.imageUrls.toString())

        if (currentImageUrls != null && newImageUrls != null) {
            for (imageUrl in currentImageUrls) {
                newImageUrls.remove(imageUrl)
            }
            _userchanges.value = _userchanges.value?.copy(imageUrls = newImageUrls)
        }
    }

    fun deleteAccount() {
        // TODO: Implement delete account functionality
    }
}
