package com.example.a029_activitylifcecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a029_activitylifcecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("test100", "onCreate")

    }

    override fun onStart() {
        super.onStart()
        Log.d("test100", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("test100", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("test100", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("test100", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("test100", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test100", "onRestart")
    }


}