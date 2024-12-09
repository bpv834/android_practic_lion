package com.lion.a10_mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InputViewModel(val inputActivity: InputActivity) : ViewModel() {
    // editTextInput - text
    val editTextInputText = MutableLiveData("")
    // textViewTest - text
    val textViewTestText = MutableLiveData<String>()

    // buttonFinish - onClick
    fun buttonFinishOnClick(){
        // Activity의 메서드를 호출한다.
        inputActivity.activityFinish()
    }

    // buttonTest - onClick
    fun buttonTestOnClick(){
        textViewTestText.value = editTextInputText.value
    }
}