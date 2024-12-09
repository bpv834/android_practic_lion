package com.lion.a11_mvvm_recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(val mainActivity: MainActivity) : ViewModel() {
    // editText - text
    val editTextText = MutableLiveData("")

    // button - onClick
    fun buttonOnClick(){
        mainActivity.addRecyclerViewItem()
    }
}