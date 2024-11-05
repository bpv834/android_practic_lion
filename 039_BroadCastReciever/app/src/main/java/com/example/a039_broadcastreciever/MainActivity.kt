package com.example.a039_broadcastreciever

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    // BR 객체생성
    val br = TestReciever()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 안드로이드 8.0 부터는 BR의 이름을 코드로 등록하고 해제해야 한다.
        // 개발자가 추가 등록하는 BR는 BR를 가지고 있는 애플리케이션이 실행되어야만
        // 사용이 가능하다.
        // 일반적으로 Service (화면을 가지지 않는 실행단위)에서 BR 를 등록하고
        // Service가 종료될 때 해제하는 방식으로 구현을 한다.
        // 실제로 현업에서는 그냥 시스템 감시용으로만 사용한다.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // BR를 등록한다.
            val filter = IntentFilter("com.lion.testbr")
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                registerReceiver(br, filter, RECEIVER_EXPORTED)
            } else {
                registerReceiver(br, filter)
            }
        }

        // Activity가 종료될 때 BR 등록을 해제한다.

    }

    // Activity가 종료될 때 BR 등록을 해제해준다.
    override fun onDestroy() {
        super.onDestroy()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            unregisterReceiver(br)
        }
    }
}