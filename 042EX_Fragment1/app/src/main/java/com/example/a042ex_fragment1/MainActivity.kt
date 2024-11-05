package com.example.a042ex_fragment1

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.a042ex_fragment1.databinding.ActivityMainBinding
import com.example.a042ex_fragment1.fragment.InputFragment
import com.example.a042ex_fragment1.fragment.MainFragment
import com.example.a042ex_fragment1.fragment.ShowFragment
import com.google.android.material.transition.MaterialSharedAxis

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    val dataList = mutableListOf<DataModel>()


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

        //키보드가 올라간 것이 있다면 내려준다
        hideSoftInput()

        //MainFragment 를 설정한다.
        replaceFragment(FragmentName.MAIN_FRAGMENT,false,null)
    }
    // 프래그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack:Boolean, dataBundle:Bundle? ){
        // 프래그먼트 객체
        val newFragment = when(fragmentName){
            FragmentName.MAIN_FRAGMENT -> MainFragment()
            FragmentName.INPUT_FRAGMENT -> InputFragment()
            FragmentName.SHOW_FRAGMENT -> ShowFragment()
        }


        // 프래그먼트 교체
        supportFragmentManager.commit {
            //Animation 효과
            newFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
            newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)

            // bundle 객체가 null이 아니라면
            if(dataBundle != null){
                newFragment.arguments = dataBundle
            }

            // activity_main 에 있는 프래그먼트 뷰에 newFragment 뷰를 얹는다
            replace(R.id.fragmentContainerView, newFragment)
            if(isAddToBackStack){
                addToBackStack(fragmentName.str)
            }
        }
    }
    // 키보드를 내려주는 메서드
    fun hideSoftInput(){
        // 현재 포커스를 가지고 있는 view가 있다면
        if(currentFocus != null){
            // 키보드를 내린다.
            val inputManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            // 포커스를 제거한다.
            currentFocus?.clearFocus()
        }
    }


    // 프래그먼트를 BackStack에서 제거하는 메서드
    fun removeFragment(fragmentName: FragmentName){
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}

// 프래그먼트들을 나타내는 값들
enum class FragmentName(var number:Int, var str:String){
    MAIN_FRAGMENT(1, "MainFragment"),
    INPUT_FRAGMENT(2, "InputFragment"),
    SHOW_FRAGMENT(3, "ShowFragment")
}