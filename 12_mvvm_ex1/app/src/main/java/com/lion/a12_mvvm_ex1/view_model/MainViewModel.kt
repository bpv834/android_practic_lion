package com.lion.a12_mvvm_ex1.view_model

import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_ex1.MainActivity

class MainViewModel(val mainActivity: MainActivity): ViewModel() {

    fun btnShowDialogClick() {
        mainActivity.inputBtn()
    }
}