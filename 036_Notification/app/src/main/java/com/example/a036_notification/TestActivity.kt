package com.example.a036_notification

import android.app.NotificationManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a036_notification.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {
    lateinit var activityTestBinding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityTestBinding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(activityTestBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityTestBinding.apply {
            textView.text = "${intent.getIntExtra("data1", 0)}\n"
            textView.append("${intent.getIntExtra("data2", 0)}")
        }

        //action 누르고 실행됐을 때 메시지 없애준다.
        val manage = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manage.cancel(300)
    }
}