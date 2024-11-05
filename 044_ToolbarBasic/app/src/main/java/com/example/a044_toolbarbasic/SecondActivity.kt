package com.example.a044_toolbarbasic

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a044_toolbarbasic.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var activitySecondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(activitySecondBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activitySecondBinding.apply {
            materialToolbar2.title = " SecondActivity"
            // 좌측 상단에 뒤로가기 버튼 활성화
            materialToolbar2.setNavigationIcon(android.R.drawable.ic_menu_more)
            // 좌측 상단의 버튼을 누르면 동작하는 리스너
            materialToolbar2.setNavigationOnClickListener {
                finish()
            }
        }
    }
}