package com.example.a042_fragment

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.a042_fragment.databinding.ActivityMainBinding

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
                // FirstFragment 객체를 생성한다.
                val firstFragment = FirstFragment()
                // Fragment 관리자를 통해서 프래그 먼트를 보여준다.
//                val fragmentTransaction =  supportFragmentManager.beginTransaction()
//                //fragmentTransaction.add(R.id.fragmentContainerView, firstFragment)
//                fragmentTransaction.replace(R.id.fragmentContainerView, firstFragment)
//                fragmentTransaction.commit()
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, firstFragment)
                    //BackStack 에 포함시킨다.
                    addToBackStack("First")
                }
            }
            button2.setOnClickListener{
                // SecondFragment 객체를 생성한다.
                val secondFragment = SecondFragment()
                // Fragment 관리자를 통해서 프래그 먼트를 보여준다.
//                val fragmentTransaction =  supportFragmentManager.beginTransaction()
//                // fragmentTransaction.add(R.id.fragmentContainerView, secondFragment)
//                fragmentTransaction.replace(R.id.fragmentContainerView, secondFragment)
//                fragmentTransaction.commit()
                supportFragmentManager.commit {
                    replace(R.id.fragmentContainerView, secondFragment)
                }
            }
            button3.setOnClickListener {
                // BackStack에 포함되어 있는 프래그먼트 개수의 0보다 크면
                // 백스택에서 프래그먼트를 제거한다.
                if(supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    // 백스택에 프래그먼트가 없으면 액티비티를 종료한다.
                    finish()
                }
            }
        }
    }
}