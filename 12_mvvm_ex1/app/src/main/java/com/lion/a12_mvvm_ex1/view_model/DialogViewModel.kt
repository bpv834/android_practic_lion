package com.lion.a12_mvvm_ex1.view_model

import android.content.DialogInterface
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_ex1.MainActivity

class DialogViewModel (val mainActivity: MainActivity) : ViewModel(){
    val editTextInputStr = MutableLiveData<String>()

    fun btnSubmitOnClick() {
        Log.d("test100","submitClick")
        mainActivity.submitBtn(editTextInputStr.value!!)

    }
}