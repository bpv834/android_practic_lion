package com.lion.a09_mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel: ViewModel() {
    // 프로퍼티를 정의한다.
    // ViewModel의 프로퍼티는 UI의 요소의 속성값과 동일하게 해줘야 한다.
    // TextView의 text 속성과 연결될 프로퍼티
    val textViewResultText = MutableLiveData<String>()

}