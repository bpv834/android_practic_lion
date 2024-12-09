package com.lion.a12_mvvm_student.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.a12_mvvm_student.ShowActivity

class ShowViewModel(val showActivity: ShowActivity):ViewModel() {
    // textViewShowName - text
    val textViewShowName = MutableLiveData<String>()
    // textViewShowAge - text
    val textViewShowAge = MutableLiveData<String>()
    // textViewShowKorean - text
    val textViewShowKorean = MutableLiveData<String>()
    // textViewShowEnglish - text
    val textViewShowEnglish = MutableLiveData<String>()
    // textViewShowMath - text
    val textViewShowMath = MutableLiveData<String>()
}