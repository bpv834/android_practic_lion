package com.lion.a12_mvvm_student.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_student.InputActivity

class InputViewModel(val inputActivity: InputActivity) : ViewModel() {
    // editTextInputName - text
    val editTextInputName = MutableLiveData<String>()
    // editTextInputAge - text
    val editTextInputAge = MutableLiveData<String>()
    // editTextInputKorean - text
    val editTextInputKorean = MutableLiveData<String>()
    // editTextInputEnglish - text
    val editTextInputEnglish = MutableLiveData<String>()
    // editTextInputMath - text
    val editTextInputMath = MutableLiveData<String>()
    // buttonInputSubmit - onClick
    fun buttonInputSubmitOnClick(){
        inputActivity.finishInputActivity()
    }
}