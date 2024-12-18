package com.lion.boardproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.lion.boardproject.R
import com.lion.boardproject.UserActivity
import com.lion.boardproject.UserFragmentName
import com.lion.boardproject.databinding.FragmentLoginBinding
import com.lion.boardproject.databinding.FragmentUserJoinStep2Binding
import com.lion.boardproject.viewmodel.UserJoinStep2ViewModel

class UserJoinStep2Fragment : Fragment() {

    lateinit var fragmentUserJoinStep2Binding: FragmentUserJoinStep2Binding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentUserJoinStep2Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_join_step2, container, false)
        fragmentUserJoinStep2Binding.userJoinStep2ViewModel = UserJoinStep2ViewModel(this@UserJoinStep2Fragment)
        fragmentUserJoinStep2Binding.lifecycleOwner = this@UserJoinStep2Fragment

        userActivity = activity as UserActivity


        return fragmentUserJoinStep2Binding.root
    }

    // 이전 화면으로 돌아가는 메서드
    fun movePrevFragment(){
        userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP2_FRAGMENT)
    }

    // 가입 완료 처리 메서드
    fun proUserJoin(){
        fragmentUserJoinStep2Binding.apply {
            // 입력 검사
            if(userJoinStep2ViewModel?.textFieldUserJoinStep2NickNameEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("닉네임 입력", "닉네임을 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserJoinStep2NickName.editText!!)
                }
                return
            }
            if(userJoinStep2ViewModel?.textFieldUserJoinStep2AgeEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("나이 입력", "나이를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserJoinStep2Age.editText!!)
                }
                return
            }

            userActivity.showMessageDialog("가입 완료", "가입이 완료되었습니다\n로그인해주세요", "확인"){
                userActivity.removeFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT)
            }
        }
    }
}