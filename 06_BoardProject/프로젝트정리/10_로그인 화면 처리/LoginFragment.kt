package com.lion.boardproject.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lion.boardproject.BoardActivity
import com.lion.boardproject.MainActivity
import com.lion.boardproject.R
import com.lion.boardproject.UserActivity
import com.lion.boardproject.UserFragmentName
import com.lion.boardproject.databinding.FragmentLoginBinding
import com.lion.boardproject.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var userActivity: UserActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        fragmentLoginBinding.loginViewModel = LoginViewModel(this@LoginFragment)
        fragmentLoginBinding.lifecycleOwner = this@LoginFragment

        userActivity = activity as UserActivity

        // 툴바를 구성하는 메서드 호출
        settingToolbar()

        return fragmentLoginBinding.root
    }
    
    // 툴바를 구성하는 메서드
    fun settingToolbar(){
        fragmentLoginBinding.loginViewModel?.apply {
            toolbarUserLoginTitle.value = "로그인"
        }
    }

    // 회원 가입 화면으로 이동시키는 메서드
    fun moveToUserJoinStep1(){
        userActivity.replaceFragment(UserFragmentName.USER_JOIN_STEP1_FRAGMENT, true, true, null)
    }

    // 로그인 처리 메서드
    fun proLogin(){
        fragmentLoginBinding.apply {
            // 입력 요소 검사
            if(loginViewModel?.textFieldUserLoginIdEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("아이디 입력", "아이디를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserLoginId.editText!!)
                }
                return
            }
            if(loginViewModel?.textFieldUserLoginPwEditTextText?.value?.isEmpty()!!){
                userActivity.showMessageDialog("비밀번호 입력", "비밀번호를 입력해주세요", "확인"){
                    userActivity.showSoftInput(textFieldUserLoginPw.editText!!)
                }
                return
            }

            // BoardActivity를 실행하고 현재 Activity를 종료한다.
            val boardIntent = Intent(userActivity, BoardActivity::class.java)
            startActivity(boardIntent)
            userActivity.finish()
        }
    }
}