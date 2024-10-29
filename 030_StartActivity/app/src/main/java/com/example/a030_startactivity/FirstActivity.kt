package com.example.a030_startactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a030_startactivity.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var activityFirstBinding : ActivityFirstBinding
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

            //현재 Activity를 실행하기 위해 사용됐던 Intent는 실행된 Activity로 전달된다.


            // 현재 Activity를 실행하기 위해 사용되었던 Intent는 실행된 Activity로 전달된다.
            textView.text = "SecondActivity\n"
            textView.append("data1 : ${intent.getIntExtra("data1", 0)}\n")
            textView.append("data2 : ${intent.getDoubleExtra("data2", 0.0)}\n")
            textView.append("data3 : ${intent.getBooleanExtra("data3", false)}\n")
            textView.append("data4 : ${intent.getStringExtra("data4")}")

            button4.setOnClickListener {
                // 현재 Activity를 종료한다.
                // finish()

                // 작업의 결과를 나타내는 값을 지정하여 종료한다.
                // setResult(RESULT_OK)
                // finish()

                // 만약 현재 Activity가 종료되고 이전 Activity가 보여질 때 데이터를 전달하고 싶다면
                // Intent 객체를 생성하여 담아준다.
                val finishIntent = Intent()
                finishIntent.putExtra("value1" , 200)
                finishIntent.putExtra("value2", 22.22)
                finishIntent.putExtra("value3", false)
                finishIntent.putExtra("value4", "문자열 2")
                // 데이터를 담은 Intent를 설정한다.
                setResult(RESULT_OK, finishIntent)
                finish()
            }
        }
    }


}
