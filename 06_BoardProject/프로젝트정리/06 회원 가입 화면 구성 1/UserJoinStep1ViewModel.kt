package com.lion.boardproject.viewmodel

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.lion.boardproject.UserFragmentName
import com.lion.boardproject.fragment.UserJoinStep1Fragment

data class UserJoinStep1ViewModel(val userJoinStep1Fragment: UserJoinStep1Fragment) : ViewModel(){
    // toolbarUserJoinStep1 - title
    val toolbarUserLoginTitle = MutableLiveData<String>()
    // toolbarUserJoinStep1 - navigationIcon
    val toolbarUserLoginNavigationIcon = MutableLiveData<Int>()

    companion object{
        // toolbarUserJoinStep1 - onNavigationClickUserJoinStep1
        @JvmStatic
        // xml 에 설정할 속성이름을 설정한다.
        @BindingAdapter("onNavigationClickUserJoinStep1")
        // 호출되는 메서드를 구현한다.
        // 첫 번째 매개변수 : 이 속성이 설정되어 있는 View 객체
        // 그 이후 : 전달해주는 값이 들어온다. xml 에서는 ViewModel이 가는 프로퍼티에 접근할 수 있기 때문에
        // 이것을 통해 Fragment객체를 받아 사용할것이다.
        fun onNavigationClickUserJoinStep1(materialToolbar:MaterialToolbar, userJoinStep1Fragment: UserJoinStep1Fragment){
            materialToolbar.setNavigationOnClickListener {
                // 이전으로 돌아간다.
                userJoinStep1Fragment.userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT)
            }
        }
    }
}
