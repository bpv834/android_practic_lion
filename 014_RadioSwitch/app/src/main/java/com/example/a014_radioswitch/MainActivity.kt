package com.example.a014_radioswitch

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a014_radioswitch.databinding.ActivityMainBinding

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
                //스위치
                //스위치는 모든 스위치 대해서 처리해줘야  한다.
                textView.text = if (switch1.isChecked) {
                    "Switch 1 on 상태 입니다."
                } else {
                    "Switch 1 은 OFF 상태 입니다."
                }

                textView2.text = if (switch2.isChecked) {
                    "Switch 2 =는 ON 상태 입니다."
                } else {
                    "Switch 2  는 OFF 상태 입니다."
                }


                // 라디오 버튼
                // 라디오 그룹을 통해 체크 되어 있는 라디오 버튼의 ID를 파악한 다음
                // 이를 통해 분기해 처리한다.
                when(radioGroup1.checkedRadioButtonId){
                    R.id.radioButton -> textView3.text = "라디오 버튼 1"
                    R.id.radioButton2 -> textView3.text = "라디오 버튼 2"
                    R.id.radioButton3 -> textView3.text = "라디오 버튼 3"
                }
            }

            button2.setOnClickListener {
                //스위치는 각각 독립적이기 때문에 각각해줘야 한다.
                switch1.isChecked = false
                switch2.isChecked = true
                // 라디오는 그룹을 통해 설정한다.
                radioGroup1.check(R.id.radioButton3)
            }

            button3.setOnClickListener {
                switch1.toggle()
                switch2.toggle()
            }

            //isChecked : onf/off 상태 값
            switch1.setOnCheckedChangeListener { buttonView, isChecked ->
                textView.text = if(isChecked){
                    "switch1이 on 상태가 됐다."
                }else{
                    "Switch1 이 off 가 됐다."
                }
            }
            // checkedId : 체크된 라이도 버튼의 ID 값
            radioGroup1.setOnCheckedChangeListener { group, checkedId ->
                when(checkedId){
                    R.id.radioButton -> textView.text = " 라디오1이 선택"
                    R.id.radioButton2 -> textView2.text = "라디오2 선택"
                    R.id.radioButton3 -> textView3.text = "라디오 3 선택"
                }
            }
        }
    }
}