package com.example.a037_activityaction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a037_activityaction.databinding.ActivityMainBinding

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
                // 보여질 위치에 대한 위도와 경도를 설정한 Uri 객체를 생성한다.
                val uri = Uri.parse("geo:37.243243,131.861601")
                // 인텐트를 생성한다.
                val geoIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(geoIntent)
            }

            button2.setOnClickListener {
                // 띄울 사이트의 주소를 가지고 있는 생성한다.
                val uri = Uri.parse("")
                button2.setOnClickListener {
                    // 띄울 사이트의 주소를 가지고 있는 생성한다
                    val uri = Uri.parse("https://developer.android.com")
                    val httpIntent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(httpIntent)
                }
            }
            button3.setOnClickListener {
                val uri = Uri.parse("tel : 12345678")
                val dialIntent = Intent(Intent.ACTION_DIAL,uri)
                startActivity(dialIntent)
            }
        }
    }
}