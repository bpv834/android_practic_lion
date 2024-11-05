package com.example.a051_topappbar2

import android.os.Bundle
import android.view.Gravity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a051_topappbar2.databinding.ActivityMainBinding

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
            // 툴바에 메뉴 넣어주기
            toolbar.inflateMenu(R.menu.main_menu)

            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.item1 -> textView.text = "메뉴1을 눌렀습니다"
                    R.id.item2 -> textView.text = "메뉴2를 눌렀습니다"
                }
                true
            }

            toolbar.setNavigationIcon(android.R.drawable.ic_menu_agenda)
            toolbar.setNavigationOnClickListener {
                textView.text = "네비게이션 아이콘을 눌렀습니다"
            }
            // toolbar.title = "Toolbar의 타이틀"
            toolbarLayout.title = "title"
            // CollapsingToolbarLayout이 확장된 상태에서 표시되는 제목의 텍스트를 Material Design 스타일의 Display1 크기와 스타일로 표시하도록 설정하는 것입니다.
            toolbarLayout.setExpandedTitleTextAppearance(android.R.style.TextAppearance_Material_Display1)
            // 타이틀 중앙배치
            toolbarLayout.expandedTitleGravity = Gravity.END
        }
    }
}