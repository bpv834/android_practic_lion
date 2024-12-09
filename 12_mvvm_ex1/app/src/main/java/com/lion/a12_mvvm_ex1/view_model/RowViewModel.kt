package com.lion.a12_mvvm_ex1.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_ex1.MainActivity

class RowViewModel (val mainActivity: MainActivity): ViewModel(){
    val textViewRow = MutableLiveData("")
}