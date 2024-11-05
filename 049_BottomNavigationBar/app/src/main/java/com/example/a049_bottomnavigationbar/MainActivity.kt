package com.example.a049_bottomnavigationbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.a049_bottomnavigationbar.databinding.ActivityMainBinding

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
        activityMainBinding.apply {
            // BottomNavigationBar 의 메뉴를 설정
            // layout xml에서 menu라는 속성을 사용해도 된다.
            bottomNavigationView.inflateMenu(R.menu.bottom_nav)
            // 선택돼있을 메뉴를 설정
            // 지금 보이는 화면에 대한 메뉴 항목과 동일하게 설정해주세요.
            // 초기 화면 프레그먼트 when 구문에 초기에 it으로 들어갈 id
            bottomNavigationView.selectedItemId = R.id.bottomItem1
            // BottomNavigationView 하단 메뉴를 누르면 동작하는 리스너
            // it : 현재 사용자가 누른 메뉴 객체
            bottomNavigationView.setOnItemSelectedListener {
                // 보여질 Fragment 객체 생성
                val newFragment = when (it.itemId) {
                    R.id.bottomItem1 -> FirstFragment()
                    R.id.bottomItem2 -> SecondFragment()
                    R.id.bottomItem3 -> ThirdFragment()
                    else -> FourthFragment()
                }

                supportFragmentManager.commit {
                    // fragmentView와 Fragment 객체를 연결
                    replace(R.id.fragmentContainerView, newFragment)
                    // 연결옵션
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                }
                true

            }
        }
    }
}