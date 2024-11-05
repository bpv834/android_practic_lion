package com.example.a050_topappbar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a050_topappbar.databinding.ActivityMainBinding

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
            // 타이틀
            materialToolbar.title = "Toolbar"
            // 타이틀을 중앙에 배치한다
            materialToolbar.isTitleCentered = true
            // 메뉴 설정한다.
            // layout xml에서 menu 속성을 통해 설정해도 된다.
            materialToolbar.inflateMenu(R.menu.main_menu)
            // 메뉴를 눌렀을 때
            materialToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item1 -> textView.text = "메뉴 1을 눌렀습니다"
                    R.id.item2 -> textView.text = "메뉴 2를 눌렀습니다"
                }
                true
            }
            // 네비게이션 아이콘
            // 좌측 상단 아이콘, 보통 뒤로가기 놓는 곳
            materialToolbar.setNavigationIcon(android.R.drawable.ic_menu_call)
            // 네비게이션 아이콘을 눌렀을 때
            materialToolbar.setNavigationOnClickListener {
                textView.text = "네비게이션 아이콘을 눌렀습니다"
            }
        }


    }
}