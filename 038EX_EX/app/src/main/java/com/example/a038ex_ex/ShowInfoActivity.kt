package com.example.a038ex_ex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a038ex_ex.databinding.ActivityMainBinding
import com.example.a038ex_ex.databinding.ActivityShowInfoBinding

class ShowInfoActivity : AppCompatActivity() {
    lateinit var activityShowInputBinding: ActivityShowInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityShowInputBinding = ActivityShowInfoBinding.inflate(layoutInflater)
        setContentView(activityShowInputBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityShowInputBinding.apply {
            txtNameActivityShowInfo.text = "이름 : ${intent.getStringExtra("name")}"
            txtAgeActivityShowInfo.text = "나이 : ${intent.getStringExtra("age")}"
            txtKorScoreActivityShowInfo.text = "국어 점수 : ${intent.getStringExtra("korScore")}"

        }
    }
}