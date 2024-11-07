package com.lion.a055_applicationclass

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a055_applicationclass.databinding.ActivityMainBinding


// Application 클래스
// Application을 상속받은 클래스로써 AndroidManifest.xml 의 Application 태그의 name 속에 지정한다.
// 이 클래스의 객체는 안드로이드 OS가 생성하며 전체 애플케이션에서 딱 하나만 생성된다.
// 같은 애플리케이션 내부라면 어디서든지 데이터를 가져다 사용할 수 있다
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
            button.setOnClickListener {
                // Application 객체를 추출한다.
                val appClass = application as AppClass
                appClass.value1 = 100
                appClass.value2 = 11.11
                appClass.value3 = "안녕하세요"

                val secondIntent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(secondIntent)
            }
        }
    }
}