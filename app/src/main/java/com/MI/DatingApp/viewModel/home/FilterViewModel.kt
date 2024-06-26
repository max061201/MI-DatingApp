package com.MI.DatingApp.viewModel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.filter.FilterData
import com.MI.DatingApp.viewModel.user.UserViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterViewModel : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    var firebaseRealTimeDB = FirebaseDatabase.getInstance().getReference("Users")

    private val _filterData = MutableStateFlow(FilterData())
    val filterData: StateFlow<FilterData> = _filterData

    private val _isFilterVisible = MutableStateFlow(false)
    val isFilterVisible: StateFlow<Boolean> = _isFilterVisible
    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData


    fun setGender(gender: String) {
        _filterData.value = _filterData.value.copy(gender = gender)
    }

    fun toggleFilterVisibility() {
        _isFilterVisible.value = !_isFilterVisible.value
    }

    /**
    Set the gender the user is looking for selected in the filter in the Realtime firebase
     */
    fun updateFilterData() {
        val currentFilterData = _filterData.value
        // Update CurrentUser gender
        currentUserLiveData.let { currentUser ->
            val updatedUser = currentUser.value?.copy(genderLookingFor = currentFilterData.gender)
            if (updatedUser != null) {
                CurrentUser.setUser(updatedUser)
                 firebaseRealTimeDB.child(updatedUser.id).setValue(updatedUser)

            }
        }

    }
}
