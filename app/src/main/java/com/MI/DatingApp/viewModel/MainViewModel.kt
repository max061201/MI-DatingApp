package com.MI.DatingApp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _number = MutableLiveData(0)
    val number: LiveData<Int> get() = _number

    private val _text = MutableLiveData("")
    val text: LiveData<String> get() = _text

    fun incCount(){
        _number.value = _number.value?.plus(1)
    }

    fun onTextChanged(newText: String) {
        _text.value = newText
    }
}
