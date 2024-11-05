package com.example.a044_toolbarbasic

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a044_toolbarbasic.databinding.ActivityMainBinding

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
            //Toolbar
            // title
            materialToolbar.title ="MainActivity"
            // 상단 메뉴
            materialToolbar.inflateMenu(R.menu.main_menu)
            // 상단 메뉴를 눌렀을 때 동작할 리스너
            // it : 사용자가 누른 메뉴의 객체
            materialToolbar.setOnMenuItemClickListener {
                // 메뉴 아이디로 분기
                when (it.itemId) {
                    R.id.item1 -> textView.text = "메뉴1을 눌렀습니다"
                    R.id.item2 -> textView.text = "메뉴2를 눌렀습니다"
                }
                true
            }
            button.setOnClickListener {
                val secondIntent = Intent(this@MainActivity, SecondActivity :: class.java)
                startActivity(secondIntent)
            }
        }
    }
}