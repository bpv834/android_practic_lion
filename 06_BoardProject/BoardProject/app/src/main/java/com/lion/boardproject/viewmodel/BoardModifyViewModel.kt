package com.lion.boardproject.viewmodel

import BoardModifyFragment
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.MaterialToolbar

class BoardModifyViewModel(val boardModifyFragment: BoardModifyFragment) : ViewModel() {

    // textFieldBoardModifyTitle - text
    val textFieldBoardModifyTitleText = MutableLiveData(" ")
    // textFieldBoardModifyText - text
    val textFieldBoardModifyTextText = MutableLiveData(" ")


    companion object{
        // toolbarBoardModify - onNavigationClickBoardModify
        @JvmStatic

        @BindingAdapter("onNavigationClickBoardModify")
        fun onNavigationClickBoardModify(materialToolbar: MaterialToolbar, boardModifyFragment: BoardModifyFragment){
            materialToolbar.setNavigationOnClickListener {
            }
        }
    }
}