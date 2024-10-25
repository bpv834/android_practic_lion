package com.example.a015_checkbox

import android.os.Bundle
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a015_checkbox.databinding.ActivityMainBinding
import com.google.android.material.checkbox.MaterialCheckBox

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
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

            // 이벤트 동작 여부 변수
            var isParentUpdating = true
            var isChildrenUpdating = true
            // 전체 체크 박스
            // 체크 상태가 변경되었을 때
            // state : 체크 상태값
            checBoxAll.addOnCheckedStateChangedListener { checkBox, state ->

                // 동물 체크박스의 이벤트 동작을 막았다면 종료시킨다
                if(isParentUpdating == false){
                    return@addOnCheckedStateChangedListener
                }

                //하위 체크박스들의 이벤트 동작을 막아준다.
                isChildrenUpdating =false


                when (state) {
                    // 체크 상태일 때
                    MaterialCheckBox.STATE_CHECKED -> {
                        // 모든 체크박스를 체크한다.
                        checkBoxDog.isChecked = true
                        checkBoxCat.isChecked = true
                        checkBoxParrot.isChecked = true
                    }
                    // 체크 상태가 아닐 때
                    MaterialCheckBox.STATE_UNCHECKED -> {
                        // 모든 체크스를 체크 해제한다.
                        checkBoxDog.isChecked = false
                        checkBoxCat.isChecked = false
                        checkBoxParrot.isChecked = false
                    }
                }

                //하위 체크박스들의 리스너 동작을 풀어준다.
                isChildrenUpdating = true
            }

            // 체크박스들에 설정할 이벤트 람다식
            val checkBoxListener = OnCheckedChangeListener { buttonView, isChecked ->

                // 하위 체크박스의 리스너 동작을 막았다면 중단시킨다.
                if(isChildrenUpdating == false){
                    return@OnCheckedChangeListener
                }

                //동물 체크박스의 리스너 동작을 막아준다.
                isParentUpdating = false


                // 체크돼 있는 체크박스의 개수를 담을 변수
                var checkedCount = 0

                //체크박스들을 검사한다.
                if (checkBoxDog.isChecked) {
                    checkedCount++
                }
                if (checkBoxCat.isChecked) {
                    checkedCount++
                }
                if (checkBoxParrot.isChecked) {
                    checkedCount++
                }

                // 체크박스 개수에 따라 상태를 설정한다.
                //받는 상수 모양에 따라 체크박스 모양이 바뀜 체크 , - , 빈박스
                checBoxAll.checkedState = if (checkedCount == 0) {
                    MaterialCheckBox.STATE_UNCHECKED
                } else if (checkedCount == 3) {
                    MaterialCheckBox.STATE_CHECKED
                } else {
                    MaterialCheckBox.STATE_INDETERMINATE
                }

                //동물 체크박스의 리스너 동작을 허용한다.
                isParentUpdating = true
            }
            // 모든 체크 박스에 설정
            checkBoxDog.setOnCheckedChangeListener(checkBoxListener)
            checkBoxCat.setOnCheckedChangeListener(checkBoxListener)
            checkBoxParrot.setOnCheckedChangeListener(checkBoxListener)


        }
    }
}
