package com.lion.a12_mvvm_student.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_student.MainActivity

class RowViewModel(val mainActivity: MainActivity) : ViewModel() {
    // textViewRow - text
    val textViewRow = MutableLiveData("")
}