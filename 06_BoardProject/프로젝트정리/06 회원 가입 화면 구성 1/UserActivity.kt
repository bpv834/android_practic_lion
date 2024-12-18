package com.lion.boardproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.transition.MaterialSharedAxis
import com.lion.boardproject.databinding.ActivityUserBinding
import com.lion.boardproject.fragment.LoginFragment
import com.lion.boardproject.fragment.UserJoinStep1Fragment

class UserActivity : AppCompatActivity() {

    lateinit var activityUserBinding:ActivityUserBinding

    // 현재 Fragment와 다음 Fragment를 담을 변수(애니메이션 이동 때문에...)
    var newFragment:Fragment? = null
    var oldFragment:Fragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityUserBinding = DataBindingUtil.setContentView(this@UserActivity, R.layout.activity_user)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 첫번째 Fragment를 설정한다.
        replaceFragment(UserFragmentName.USER_LOGIN_FRAGMENT, false, false, null)
    }

    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: UserFragmentName, isAddToBackStack:Boolean, animate:Boolean, dataBundle: Bundle?){
        // newFragment가 null이 아니라면 oldFragment 변수에 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }
        // 프래그먼트 객체
        newFragment = when(fragmentName){
            // 로그인 화면
            UserFragmentName.USER_LOGIN_FRAGMENT -> LoginFragment()
            // 회원 가입 과정1 화면
            UserFragmentName.USER_JOIN_STEP1_FRAGMENT -> UserJoinStep1Fragment()
        }

        // bundle 객체가 null이 아니라면
        if(dataBundle != null){
            newFragment?.arguments = dataBundle
        }

        // 프래그먼트 교체
        supportFragmentManager.commit {

            if(animate) {
                // 만약 이전 프래그먼트가 있다면
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                }

                newFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            }

            replace(R.id.fragmentContainerViewUser, newFragment!!)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }


    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: UserFragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

// 프래그먼트들을 나타내는 값들
enum class UserFragmentName(var number:Int, var str:String){
    // 로그인 화면
    USER_LOGIN_FRAGMENT(1, "UserLoginFragment"),
    // 회원 가입 화면
    USER_JOIN_STEP1_FRAGMENT(2, "UserJoinStep1Fragment"),
}