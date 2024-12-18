package com.lion.boardproject.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.boardproject.R
import com.lion.boardproject.UserActivity
import com.lion.boardproject.UserFragmentName
import com.lion.boardproject.databinding.FragmentUserJoinStep1Binding
import com.lion.boardproject.viewmodel.UserJoinStep1ViewModel


class UserJoinStep1Fragment : Fragment() {

    lateinit var fragmentUserJoinStep1Binding: FragmentUserJoinStep1Binding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentUserJoinStep1Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_join_step1, container, false)
        fragmentUserJoinStep1Binding.userJoinStep1ViewModel = UserJoinStep1ViewModel(this@UserJoinStep1Fragment)
        fragmentUserJoinStep1Binding.lifecycleOwner = this@UserJoinStep1Fragment

        userActivity = activity as UserActivity

        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()

        return fragmentUserJoinStep1Binding.root
    }
    
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentUserJoinStep1Binding.userJoinStep1ViewModel?.apply {
            // 타이틀
            toolbarUserLoginTitle.value = "회원 가입"
            // 네비게이션 아이콘
            toolbarUserLoginNavigationIcon.value = R.drawable.arrow_back_24px
        }
    }

    // 다음 입력 화면으로 이동한다.
    fun moveToUserJoinStep2(){
        userActivity.replaceFragment(UserFragmentName.USER_JOIN_STEP2_FRAGMENT, true, true, null)
    }
}