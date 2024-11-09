package com.lion.a061_ex_roomdatabase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.google.android.material.transition.MaterialSharedAxis
import com.lion.a061_ex_roomdatabase.databinding.ActivityMainBinding
import com.lion.a061_ex_roomdatabase.fragment.FixFragment
import com.lion.a061_ex_roomdatabase.fragment.InputFragment
import com.lion.a061_ex_roomdatabase.fragment.MainFragment
import com.lion.a061_ex_roomdatabase.fragment.ShowRowFragment

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

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

        //첫 화면은 MainFragment로 설정
        replaceFragment(FragmentName.MAIN_FRAGMENT,false,null)
    }
    // 프레그먼트를 교체하는 함수
    fun replaceFragment(fragmentName: FragmentName, isAddToBackStack : Boolean, dataBundle: Bundle?){

        //프레그먼트 객체
        val newFragment = when(fragmentName){
            FragmentName.MAIN_FRAGMENT -> MainFragment()
            FragmentName.INPUT_FRAGMENT -> InputFragment()
            FragmentName.SHOW_ROW_FRAGMENT -> ShowRowFragment()
            FragmentName.FIX_FRAGMENT -> FixFragment()
            FragmentName.INPUT2_FRAGMENT -> Input2Fragment()
        }

        // bundle 객체가 null 이 아니라면 번들 주입
        if(dataBundle != null) newFragment.arguments = dataBundle


        supportFragmentManager.commit {

            // 화면전환 애니메이션
            newFragment.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
            newFragment.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
            newFragment.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
            newFragment.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)

            // 메인액티비티의 프레그먼트뷰에 newFragment를 연결 배치
            replace(R.id.fragmentContainerView, newFragment)
            // 스택에 담을지 결정
            if(isAddToBackStack) addToBackStack(fragmentName.str)
        }
    }

    // 프레그먼트 스택에서 제거 메서드
    fun removeFragment(fragmentName: FragmentName) {
        supportFragmentManager.popBackStack(fragmentName.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}




// 프래그먼트들을 나타내는 값들
enum class FragmentName(var number: Int, var str: String) {
    MAIN_FRAGMENT(1, "MainFragment"),
    INPUT_FRAGMENT(2, "InputFragment"),
    SHOW_ROW_FRAGMENT(3, "ShowRowFragment"),
    FIX_FRAGMENT(4, "FixFragment"),
    INPUT2_FRAGMENT(5, "Input2Fragment"),



}