package com.lion.a055_ex_ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.transition.MaterialSharedAxis
import com.lion.a055_ex_ui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 데이터 보관용 리스트
    val animalList = mutableListOf<DataModel>()
    val dogList = mutableListOf<DataModel>()
    val catList = mutableListOf<DataModel>()
    val parrotList = mutableListOf<DataModel>()


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
        // NavigationView 설정 메서드 호출
        settingNavigationView()
    }

    // NavigationView 설정 메서드
    fun settingNavigationView() {
        activityMainBinding.navigationViewMain.apply {
            // 제일 처음 메뉴를 선택된 상태로 설정한다.
            setCheckedItem(R.id.navigationMainAll)

            setNavigationItemSelectedListener {
                // 체크 상태를 true로 해준다.
                if (it.isCheckable) {
                    it.isChecked = true
                }

                // 전달할 데이터
                val dataBundle = Bundle()


                when (it.itemId) {
                    // 클릭된 메뉴에 따라 같은 프레그먼트 페이지에 다른 데이터를 번들을 통해 넘겨준다, 하지만 프레그먼트 클래스만 같을 뿐 각자 새로운 객체임을 주의해야함
                    // Activity 에서는 supportFragmentManager 사용, Fragment 에서는 parentFragmentManager 사용
                    R.id.navigationMainAll -> supportFragmentManager.commit {
                        dataBundle.putSerializable("dataModelList", ArrayList(animalList))
                        replaceFragment(FragmentName.RECYCLER_FRAGMENT, true, dataBundle)
                    }

                    R.id.navigationMainDog -> supportFragmentManager.commit {
                        dataBundle.putSerializable("dataModelList", ArrayList(dogList))
                        replaceFragment(FragmentName.RECYCLER_FRAGMENT, true, dataBundle)

                    }

                    R.id.navigationMainCat -> supportFragmentManager.commit {
                        dataBundle.putSerializable("dataModelList", ArrayList(catList))
                        replaceFragment(FragmentName.RECYCLER_FRAGMENT, true, dataBundle)

                    }

                    R.id.navigationMainParrot -> supportFragmentManager.commit {
                        dataBundle.putSerializable("dataModelList", ArrayList(parrotList))
                        replaceFragment(FragmentName.RECYCLER_FRAGMENT, true, dataBundle)
                    }
                }
                // NavigationView 닫기
                activityMainBinding.drawerlayoutMain.close()

                true
            }

        }
    }

    //프레그먼트를 교체하는 함수
    fun replaceFragment(
        fragmentName: FragmentName,
        isAddToBackStack: Boolean,
        dataBundle: Bundle?
    ) {
        // 프레그먼트 객체
        val newFragment = when (fragmentName) {
            FragmentName.RECYCLER_FRAGMENT -> RecyclerFragment()
            FragmentName.INPUT_DATA_FRAGMENT -> InputDataFragment()
            FragmentName.SHOW_FRAGMENT -> ShowFragment()
        }

        //프레그먼트 교체
        supportFragmentManager.commit {
            // Animation 효과
            newFragment.exitTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.reenterTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
            newFragment.enterTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
            newFragment.returnTransition =
                MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)

            // bundle 객체가 null이 아니라면 프레그먼트 아규먼트에 번들 객체 입력
            if (dataBundle != null) {
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
    }

    // 프레그먼트를 BackStack 에서 제거하는 메서드
    fun removeFragment(fragmentName: FragmentName) {
        supportFragmentManager.popBackStack(
            fragmentName.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}

// 프래그먼트들을 나타내는 값들
enum class FragmentName(var number: Int, var str: String) {
    RECYCLER_FRAGMENT(1, "RecyclerFragment"),
    INPUT_DATA_FRAGMENT(2, "InputDataFragment"),
    SHOW_FRAGMENT(3, "ShowFragment")
}