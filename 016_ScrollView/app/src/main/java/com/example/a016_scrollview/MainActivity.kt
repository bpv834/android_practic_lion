package com.example.a016_scrollview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a016_scrollview.databinding.ActivityMainBinding

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
                // 현재 스크롤 값을 출력해 본다.
                textView.text = " 세로 위치 : ${scrollView.scrollY}"
                textView2.text = "  가로 위치 위치 : ${horizontalScrollView.scrollX}"
            }

            button1.setOnClickListener {
               /* // 지정된 위치로 이동
                scrollView.scrollY = 200
                horizontalScrollView.scrollX =200*/

                //현재위치에서 지정된 만큼 이동
                // 현재위치에서 지정된 만큼 이동한다.
          /*      scrollView.scrollBy(0, 200)
                horizontalScrollView.scrollBy(200, 0)*/

             /*   // 지정된 위치로 이동시킨다(애니메이션)
                scrollView.smoothScrollTo(0, 200)
                horizontalScrollView.smoothScrollTo(200, 0)*/

              /*  //현재 위치에서 지정된 만큼 이동(애니메이션)
                scrollView.smoothScrollBy(0,200)
                horizontalScrollView.smoothScrollBy(200,0)*/
            }
            //스크롤이 변경됐을 때
            // 스크롤이 변경되었을 때
            scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                textView.text = "$oldScrollY -> $scrollY"
            }
            horizontalScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                textView2.text = "$oldScrollX -> $scrollX"
            }
        }
    }
}