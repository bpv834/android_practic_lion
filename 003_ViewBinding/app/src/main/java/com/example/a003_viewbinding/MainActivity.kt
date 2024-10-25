package com.example.a003_viewbinding

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a003_viewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ViewBinding 객체를 담을 변수

    //activity_main_xml ->
    lateinit var activityMainBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding 객체를 가져온다.
        // layoutInflater : layout 폴더에 있는 xml 파일을 통해 화면 객체를 만들 수 있는 도구
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        // ViewBinding 객체가 관리하는 화면 요소 중 최 상단에 있는 화면 요소를 지정하여 화면이 나오도록 한다.
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        //scope 함수
        activityMainBinding.apply {
           // 리스너 객체 생성
            val buttonListener = ButtonListener()
            // 버튼을 설정한다.
            //activityMainBinding.button.setOnclickListener()
            button.setOnClickListener(buttonListener)
        }
    }

    // 버튼에 설정할 리스너
    inner class ButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            activityMainBinding.apply {
                textView.text =  editTextText.text
            }
        }
    }
}