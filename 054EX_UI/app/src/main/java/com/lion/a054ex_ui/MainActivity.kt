package com.lion.a054ex_ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.lion.a054ex_ui.databinding.ActivityMainBinding
import com.lion.a054ex_ui.fragment.RecyclerFragment
import com.lion.a054ex_ui.fragment.TabFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    // 리사이클러뷰를 구성하기 위해 사용할 리스트
    val recyclerViewList = mutableListOf<String>()
   /* val recyclerViewList = Array(30){
        "데이터 $it"
    }*/


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

    // NavigtionView를 설정하는 메서드
    fun settingNavigationView(){

        activityMainBinding.navigationViewMain.apply {
            // 제일 처음 메뉴를 선택된 상태로 설정한다.
            setCheckedItem(R.id.navigation_main_item1)

            setNavigationItemSelectedListener {
                if(it.isCheckable == true){
                    // 체크상태를 true로 해준다.
                    it.isChecked = true
                }

                when(it.itemId){
                    R.id.navigation_main_item1 -> {
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainerViewMain, RecyclerFragment())
                        }
                    }
                    R.id.navigation_main_item2 -> {
                        supportFragmentManager.commit {
                            replace(R.id.fragmentContainerViewMain, TabFragment())
                        }
                    }
                }

                // NavigationView를 닫아준다.
                activityMainBinding.drawerlayoutMain.close()

                true
            }
        }
    }
}