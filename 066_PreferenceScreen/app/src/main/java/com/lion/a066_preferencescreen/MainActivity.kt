package com.lion.a066_preferencescreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.lion.a066_preferencescreen.databinding.ActivityMainBinding
import com.lion.a066_preferencescreen.fragment.ResultFragment
import com.lion.a066_preferencescreen.fragment.SettingFragment

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
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, SettingFragment())
                }
            }

            button2.setOnClickListener {
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, ResultFragment())
                }
            }
        }
    }
}