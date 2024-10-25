package com.example.a018_chip

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a018_chip.databinding.ActivityMainBinding

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
            chip.setOnClickListener {
                textView.text  ="Basic Chip을 눌렀습니다."
            }
            chip.setOnCloseIconClickListener {
                textView.text = "basic chip close 아이콘"
            }
            button.setOnClickListener {
                // Chip 그룹 안에서 체크되어 있는 Chip의 아이디를 가져온다.
                when(checkBoxGroup.checkedChipId){
                    R.id.chipCheckBox1 -> textView.text = "chip1 선택"
                    R.id.chipCheckBox2 -> textView.text = "chip2 선택"
                    R.id.chipCheckBox3 -> textView.text = "chip3 선택"
                }

                // Chip 그룹 안에서 체크되어 있는 모든 Chip들의 아이디를 가져온다.
                textView2.text = ""
                checkBoxGroup2.checkedChipIds.forEach {
                    when(it){
                        R.id.chipCheckBox4 -> textView2.append("chip4, ")
                        R.id.chipCheckBox5 -> textView2.append("chip5, ")
                        R.id.chipCheckBox6 -> textView2.append("chip6, ")
                    }
                }
            }


            // 만약 체크박스처럼 쓰겠다면 최초의 체크 상태를 코드로 설정해야 한다.
            checkBoxGroup2.check(R.id.chipCheckBox4)
            checkBoxGroup2.check(R.id.chipCheckBox5)


            button2.setOnClickListener {
                // Radio
                // 다른 Chip의 체크상태가 해제되고 지정된 Chip을 체크한다
                checkBoxGroup.check(R.id.chipCheckBox1)
                // CheckBox
                checkBoxGroup2.check(R.id.chipCheckBox4)
                checkBoxGroup2.check(R.id.chipCheckBox6)
                // 체크를 해제하고 싶다면 Chip에 직접해야 한다.
                chipCheckBox5.isChecked = false
            }

            // 반전
            // 칩에 직접해야 함
            button3.setOnClickListener {
                chipCheckBox4.toggle()
                chipCheckBox5.toggle()
                chipCheckBox6.toggle()
            }
            //Radio
            checkBoxGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                //singleSelection 이 true 인 그룹은 무조건 하나만 선택할 수 있기 때문에 첫번째 아이디 값으로 분기
                when(checkedIds[0]){
                    R.id.chipCheckBox1 -> textView.text = " chip1 이 선택"
                    R.id.chipCheckBox2 -> textView.text = " chip2 이 선택"
                    R.id.chipCheckBox3 -> textView.text = " chip3 이 선택"

                }
            }
            checkBoxGroup2.setOnCheckedStateChangeListener { group, checkedIds ->
                // SingleSelection이 true가 아닌 그룹은 모든 id 값을 가지고 반복해서 처리한다.
                textView2.text = ""
                checkedIds.forEach {
                    when(it){
                        R.id.chipCheckBox4 -> textView2.append("chip4가 선택되었습니다\n")
                        R.id.chipCheckBox5 -> textView2.append("chip5가 선택되었습니다\n")
                        R.id.chipCheckBox6 -> textView2.append("chip6이 선택되었습니다\n")
                    }
                }
            }
        }
    }
}