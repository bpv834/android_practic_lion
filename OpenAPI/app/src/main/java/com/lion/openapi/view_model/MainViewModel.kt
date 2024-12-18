package com.lion.openapi.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.openapi.MainActivity

class MainViewModel(val mainActivity: MainActivity) : ViewModel() {
    // textViewMainMessage Text
    val textViewMainMessageText = MutableLiveData("시도를 선택해주세요")
    // buttonSelectSiDo - OnClick
    fun buttonSelectSiDoOnClick(){
        mainActivity.showSelectSiDoDialog()
    }
}