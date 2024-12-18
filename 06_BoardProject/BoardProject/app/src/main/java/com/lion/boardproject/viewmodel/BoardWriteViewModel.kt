package com.lion.boardproject.viewmodel

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.lion.boardproject.fragment.BoardWriteFragment

class BoardWriteViewModel(val boardWriteFragment: BoardWriteFragment) : ViewModel() {

    // textFieldBoardWriteTitle - text
    val textFieldBoardWriteTitleText = MutableLiveData("")
    // textFieldBoardWriteText - text
    val textFieldBoardWriteTextText = MutableLiveData("")


    companion object{
        // toolbarBoardWrite - onNavigationClickBoardWrite
        @JvmStatic
        @BindingAdapter("onNavigationClickBoardWrite")
        fun onNavigationClickBoardWrite(materialToolbar: MaterialToolbar, boardWriteFragment: BoardWriteFragment){
            materialToolbar.setNavigationOnClickListener {
                boardWriteFragment.movePrevFragment()
            }
        }
    }
}