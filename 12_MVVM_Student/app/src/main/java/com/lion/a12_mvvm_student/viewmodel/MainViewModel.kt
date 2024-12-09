package com.lion.a12_mvvm_student.viewmodel

import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_student.MainActivity

class MainViewModel(val mainActivity: MainActivity) : ViewModel() {
    // buttonMain - onClick
    fun buttonMainOnClick(){
        // InputActivity를 실행하는 메서드를 호출한다.
        mainActivity.startInputActivity()
    }
}