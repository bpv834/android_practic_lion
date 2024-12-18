package com.lion.boardproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lion.boardproject.fragment.LoginFragment

data class LoginViewModel(val loginFragment: LoginFragment) : ViewModel() {
    // toolbarUserLogin - title
    val toolbarUserLoginTitle = MutableLiveData<String>()

    // buttonUserLoginJoin - onClick
    fun buttonUserLoginJoinOnClick(){
        loginFragment.moveToUserJoinStep1()
    }
}