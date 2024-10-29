package com.example.a031_startactivitycontracts

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a031_startactivitycontracts.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    lateinit var activityFirstBinding: ActivityFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityFirstBinding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(activityFirstBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityFirstBinding.apply {
            textView.text = "data1 : ${intent.getIntExtra("data1", 0)}"

            button4.setOnClickListener {
                val finishIntent = Intent()
                finishIntent.putExtra("value1", 200)
                setResult(RESULT_OK, finishIntent)
                finish()
            }
        }
    }


}
