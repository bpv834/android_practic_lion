package com.example.a040ex_todoapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.a040ex_todoapplication.databinding.ActivityMainBinding
import com.example.a040ex_todoapplication.fragment.DataModel
import com.example.a040ex_todoapplication.fragment.InputFragment
import com.example.a040ex_todoapplication.fragment.MainFragment
import com.example.a040ex_todoapplication.fragment.ShowFragment
import com.google.android.material.transition.MaterialSharedAxis


class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    var dataList = mutableListOf<DataModel>() // Data 클래스의 인스턴스들

    var notCompletedList = mutableListOf<DataModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //MainFragment 를 설정한다.
        replaceFragment(FragmentName.MAIN_FRAGMENT,false,null)
    }
    //프레그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack: Boolean, dataBundle: Bundle?) {
        // 프레그먼트 객체
        val newFragment = when(fragmentName){
            FragmentName.MAIN_FRAGMENT -> MainFragment()
            FragmentName.INPUT_FRAGMENT -> InputFragment()
            FragmentName.SHOW_FRAGMENT -> ShowFragment()
        }
        // 예시: commit 블록 전후로 로그 추가
        Log.d("MainActivity", "Replacing fragment with InputFragment")
        //프레그먼트 교체
        supportFragmentManager.commit {
            // Animation 효과
            newFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
            newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)

            // bundle 객체가 null이 아니라면
            if(dataBundle != null){
                newFragment.arguments = dataBundle
            }

            //activity_main 에 있는 프레그먼트 뷰에 newFragment 뷰를 얹는다
            replace(R.id.fragmentContainerView, newFragment)

            // backStack 에 쌓을 지 판단
            if (isAddToBackStack) {
                // 클래스 이름을 넣어준다.
                addToBackStack(fragmentName.str)
            }
        }
        Log.d("MainActivity", "Fragment transaction committed")
    }

    // 프레그먼트를 BackStack 에서 제거하는 메서드
    fun removeFragment(fragmentName: FragmentName) {
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}


// 프래그먼트들을 나타내는 값들
enum class FragmentName(var number:Int, var str:String){
    MAIN_FRAGMENT(1, "MainFragment"),
    INPUT_FRAGMENT(2, "InputFragment"),
    SHOW_FRAGMENT(3, "ShowFragment")
}