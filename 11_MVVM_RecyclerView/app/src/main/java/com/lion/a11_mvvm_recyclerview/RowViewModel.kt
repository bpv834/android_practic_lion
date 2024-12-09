package com.lion.a11_mvvm_recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowViewModel(val mainActivity: MainActivity) : ViewModel() {
    // textViewRow - text
    val textViewRowText = MutableLiveData("")
}