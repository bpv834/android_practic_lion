package com.example.a052_drawerview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a052_drawerview.databinding.ActivityMainBinding
import com.example.a052_drawerview.databinding.NavigationHeaderBinding

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
            materialToolbar.setNavigationIcon(android.R.drawable.ic_menu_agenda)
            materialToolbar.setNavigationOnClickListener {
                // 네비게이션 뷰를 보이게 한다.
                main.open()
            }

            // 네비게이션 뷰의 헤더
            val navigationHeaderBinding = NavigationHeaderBinding.inflate(layoutInflater)
            navigationHeaderBinding.textViewHeaderLarge.text = "큰 제목"
            navigationHeaderBinding.textViewHeaderSmall.text = "작은 제목"
            navigationView.addHeaderView(navigationHeaderBinding.root)

            // 메뉴
            navigationView.inflateMenu(R.menu.navigation_menu)
            navigationView.setNavigationItemSelectedListener {

                if(it.isCheckable){
                    // 체크상태를 true 로 해준다.
                    it.isChecked=true
                }

                when(it.itemId){
                    R.id.item1 -> textView.text = "메뉴1을 눌렀습니다"
                    R.id.item2 -> textView.text = "메뉴2를 눌렀습니다"
                    R.id.item3 -> textView.text = "메뉴3을 눌렀습니다"
                    R.id.item4 -> textView.text = "메뉴4를 눌렀습니다"
                    R.id.item5 -> textView.text = "메뉴5를 눌렀습니다"
                    R.id.item6 -> textView.text = "메뉴6을 눌렀습니다"
                }

                true
            }
            // 네비게이션 뷰를 닫아준다.
            main.close()
        }
    }
}