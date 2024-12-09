package com.lion.a10_mvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.lion.a10_mvvm.databinding.ActivityResultBinding


class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        // setContentView(activityResultBinding.root)
        activityResultBinding = DataBindingUtil.setContentView(this@ResultActivity, R.layout.activity_result)
        resultViewModel = ResultViewModel()
        activityResultBinding.resultViewModel = resultViewModel
        activityResultBinding.lifecycleOwner = this@ResultActivity

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityResultBinding.apply {
            val data = intent.getStringExtra("data")!!
            textViewResult.text = data
        }
    }
}