package com.example.a047_bottomappbar
import com.google.android.material.bottomappbar.BottomAppBar
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a047_bottomappbar.databinding.ActivityMainBinding

// 주요속성
// menuAlignmentMode : 메뉴의 위치를 설정한다(start : 좌측 고정, auto : FAB가 없을 때는 우측에 정렬된다)
// fabAlignmentMode : FAB의 위치. menuAliginmentMode가 auto면 fab 위치에 따라서 메뉴 정렬 위치가 변경된다.
// fabAnimationMode : FAB의 버튼의 위치를 바꿀때 적용할 애니메이션을 설정할 수 있다
// fabAnchorMode : FAB가 붙는 형태를 설정한다.(embeded(기본) : 내부에 배치된다. cradle : 살짝 올라간다)
// fabCradleMargin : FAB가 cradle일 경우 주변 여백을 설정한다

// FAB에 layout_anchor 속성에 BottomAppBar의 id를 지정하면 BottomAppBar 우측에 포함된다.


// CoordinatorLayout는 다양한 레이아웃 동작을 조정하고 뷰 간의 상호작용을 관리하기 위해 사용됩니다.
// 특히, AppBarLayout, BottomAppBar, FloatingActionButton과 같은 Material Design 컴포넌트들과 함께 사용하면 특정 상호작용이나 애니메이션을 간편하게 구현할 수 있습니다.
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
            // 메뉴를 구성해준다.
            bottomAppBar.inflateMenu(R.menu.bottom_appbar_menu)
            //메뉴를 눌렀을 때
            bottomAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item1 -> textView.text = "메뉴1을 눌렀습니다"
                    R.id.item2 -> textView.text = "메뉴2를 눌렀습니다"
                    R.id.item3 -> textView.text = "메뉴3을 눌렀습니다"
                    R.id.item4 -> textView.text = "메뉴4를 눌렀습니다"
                }
                true
            }

            button.setOnClickListener {
                textView.text
                // fabAligmentMode 를 변경
                when(bottomAppBar.fabAlignmentMode) {
                    BottomAppBar.FAB_ALIGNMENT_MODE_CENTER -> {
                        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    }
                    BottomAppBar.FAB_ALIGNMENT_MODE_END -> {
                        bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    }

                }
            }

        }
    }
}