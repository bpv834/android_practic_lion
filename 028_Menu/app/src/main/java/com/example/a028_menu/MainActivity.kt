package com.example.a028_menu

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a028_menu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // TextView에 컨텍스 메뉴를 설정한다(최근에는 Bottom Sheet를 사용하는 추세이긴 하다)
        registerForContextMenu(activityMainBinding.textView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {
            // 툴바에 옵션 메뉴를 구성한다.
            materialToolbar.inflateMenu(R.menu.toolbar_menu)
            // 메뉴의 항목을 눌럿을 때 호출되는 메서드

            materialToolbar.setOnMenuItemClickListener {
                //사용자가 누르면 메뉴의 id로 분기한다.
                when (it.itemId) {
                    R.id.optionMenu1 -> textView.text = "옵션 메뉴 1-1"
                    R.id.optionMenu2 -> textView.text = "옵션 메뉴 1-2"
                    R.id.optionMenu31 -> textView.text = " 옵션메뉴 1-3-1"
                    R.id.optionMenu32 -> textView.text = " 옵션메뉴 1-3-2"


                }
                true
            }

            button.setOnClickListener {
                // 팝업 메뉴
                val popupMenu = PopupMenu(this@MainActivity, textView)
                // 메뉴를 구성한다.
                // menuInflater : xml 파일을 통해 메뉴를 구성할 수 있는 객체
                // 첫 번째 : 메뉴를 구성하기 위해서 사용할 xml 파일
                // 두 번째 : 메뉴를 구성하기 위해서 사용할 menu 객체
                menuInflater.inflate(R.menu.toolbar_menu, popupMenu.menu)
                // 팝업 메뉴의 항목을 누르면 동작하는 리스너
                popupMenu.setOnMenuItemClickListener {
                    // 사용자가 누르면 메뉴의 id로 분기한다.
                    when (it.itemId) {
                        R.id.optionMenu1 -> textView.text = "옵션 메뉴 1-1"
                        R.id.optionMenu2 -> textView.text = "옵션 메뉴 1-2"
                        R.id.optionMenu31 -> textView.text = "옵션 메뉴 1-3-1"
                        R.id.optionMenu32 -> textView.text = "옵션 메뉴 1-3-2"
                    }
                    true
                }

                // 팝업 메뉴를 띄워준다.
                popupMenu.show()
            }

        }
    }

    // 컨텍스트 메뉴가 등록된 View를 길게 누르면 호출되는 메서드
    // 여기에서 context Menu를 구성해준다.
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // View의 아이디를 통해 어떤 View를 길게 눌렀는지 확인한다.
        when (v?.id) {
            R.id.textView -> {
                //컨텍스트 메뉴를 구성해준다.
                menu?.setHeaderTitle("텍스트 뷰의 메뉴")
                //메뉴 구성
                menuInflater.inflate(R.menu.toolbar_menu, menu)
            }
        }
    }

    // Context 메뉴의 항목을 눌렀을 때 동작하는 메서드
    // 여기에서는 사용자가 어떤 View에서 Context Menu를 띄웠는지 확인할 수 없다
    // View가 다르다고 하더라도 메뉴의 id를 모두 다르게 설정하여 menu id 별로 분기하여 처리한다.
    override fun onContextItemSelected(item: MenuItem): Boolean {
        // 사용자가 누르면 메뉴의 id로 분기한다.
        when(item.itemId){
            R.id.optionMenu1 -> activityMainBinding.textView.text = "옵션 메뉴 1-1"
            R.id.optionMenu2 -> activityMainBinding.textView.text = "옵션 메뉴 1-2"
            R.id.optionMenu31 -> activityMainBinding.textView.text = "옵션 메뉴 1-3-1"
            R.id.optionMenu32 -> activityMainBinding.textView.text = "옵션 메뉴 1-3-2"
        }

        return super.onContextItemSelected(item)
    }
}