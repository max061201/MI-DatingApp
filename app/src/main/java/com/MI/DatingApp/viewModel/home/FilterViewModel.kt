package com.MI.DatingApp.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.MI.DatingApp.model.filter.FilterData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterViewModel : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val _filterData = MutableStateFlow(FilterData())
    val filterData: StateFlow<FilterData> = _filterData

    private val _isFilterVisible = MutableStateFlow(false)
    val isFilterVisible: StateFlow<Boolean> = _isFilterVisible
    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")


    fun toggleFilterVisibility() {
        _isFilterVisible.value = !_isFilterVisible.value
    }

    fun updateFilterData(gender: String, ageRange: Pair<Int, Int>) {
        _filterData.value = FilterData(gender, ageRange)
    }

    fun saveFilterData(userId: String) {
        viewModelScope.launch {
            database.child("Users").child(userId).child("filter").setValue(_filterData.value)
        }
    }
}
