package com.example.a002_basicproject

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //화면 요소 객체를 담을 변수
    lateinit var number1 : EditText
    lateinit var number2:EditText
    lateinit var buttonPlus:Button
    lateinit var textViewResult:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //화면 요소 객체들을 가져온다.
        // xml 파일에서 화면 요소에 지정한 id 속성을 지정하여 원하는 화면 요소 객체를 가져온다.
        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        buttonPlus = findViewById(R.id.buttonPlus)
        textViewResult = findViewById(R.id.textViewResult)

        //리스너의 객체를 생성한다.
        val buttonListener = ButtonListener()
        // 버튼에 리스너를 설정해준다.
        buttonPlus.setOnClickListener(buttonListener)

    }
    // 버튼을 눌렀을 때 동작하는 리스너
    // 버튼을 누르게 되면 onClick 메서드를 호출해준다.
    inner class ButtonListener : OnClickListener{
        // 버튼을 눌렀을 때 호출되는 메서드
        override fun onClick(v: View?) {
            // 사용자가 입력한 문자열을 가져온다.
            val a1 = number1.text.toString().toInt()
            val a2 = number2.text.toString().toInt()
            // 더해준다.
            val a3 = a1 + a2
            // TextView에 출력해준다.
            textViewResult.text = "$a1 + $a2 = $a3"
        }
    }
}