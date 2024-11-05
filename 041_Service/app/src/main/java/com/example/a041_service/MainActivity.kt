package com.example.a041_service

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a041_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Service : 화면이 없는 실행단위
    // 안드로이드 8.0 부터는 Foreground Service(사용자에게 고지하고 운영하는 서비스)를 지원하며
    // Foreground Service만 사용해야 한다.
    // FOREGRDOUND_SERVICE 권한이 필요한다
    lateinit var activityMainBinding: ActivityMainBinding
    //서비스 가용에 필요한 Intent
    lateinit var serviceIntent : Intent
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

        // android 13버전 이상
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val permissionList = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
            requestPermissions(permissionList, 0)
        }

        activityMainBinding.apply {
            button.setOnClickListener {
                // 서비스를 가동시키기 위한 Intent
                serviceIntent = Intent(this@MainActivity, TestService :: class.java)
                //서비스 가동
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 포그라운드 서비스
                    startForegroundService(serviceIntent)
                } else {
                    // 백그라운드 서비스
                    startService(serviceIntent)
                }
            }
            button2.setOnClickListener{
                // 서비스 중지
                stopService(serviceIntent)

            }
        }
    }
}