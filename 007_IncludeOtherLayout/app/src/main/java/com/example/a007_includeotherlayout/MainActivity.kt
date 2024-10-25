package com.example.a007_includeotherlayout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a007_includeotherlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding : ActivityMainBinding
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
            // activity_main.xml 에 배치한 TextView
            textView.text = "안녕하세요"
            // include로 포함시킨 other_layout.xml 에 배치한 TextView
            // include의 id와 동일한 이름의 프로퍼티안에 포함시킨 layout 파일의 View들을 관리하는
            // ViewBinding 객체가 담겨져 있다.
            include1.textView2.text = "반갑습니다"
        }
    }
}