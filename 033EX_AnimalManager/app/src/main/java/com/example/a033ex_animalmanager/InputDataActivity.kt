package com.example.a033ex_animalmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a033ex_animalmanager.databinding.ActivityInputDataBinding
import com.google.android.material.chip.Chip

class InputDataActivity : AppCompatActivity() {
    lateinit var activityInputDataBinding: ActivityInputDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        activityInputDataBinding = ActivityInputDataBinding.inflate(layoutInflater)
        setContentView(activityInputDataBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        settingBtnSave()

    }

    // 제일 마지막에 있는 입력 요소 구성
    fun settingBtnSave() {
        activityInputDataBinding.apply {
            var type = "기본"
            var gender = "기본"
            val list = ArrayList<String>()


            typeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                //singleSelection 이 true 인 그룹은 무조건 하나만 선택할 수 있기 때문에 첫번째 아이디 값으로 분기
                when (checkedIds[0]) {
                    R.id.chipTypeDog -> type = "강아지"
                    R.id.chipTypeCat -> type = "고양이"
                    R.id.chipTypeParrot -> type = "앵무새"

                }
            }

            // 성별 칩의 클릭 리스너
            genderChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                //singleSelection 이 true 인 그룹은 무조건 하나만 선택할 수 있기 때문에 첫번째 아이디 값으로 분기
                when (checkedIds[0]) {
                    R.id.chipGenderMale -> gender = "남자"
                    R.id.chipGenderFemale -> gender = "여자"

                }
            }

            // 그룹 내의 모든 chip id 값을 가지고 반복해서 처리한다.
            favoriteSnacksChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                list.clear() // 리스트 초기화
                checkedIds.forEach {
                    when (it) {
                        R.id.chipFavoriteSnacksApple -> {
                            val txt = favoriteSnacksChipGroup.findViewById<Chip>(it).text.toString()
                            list.add(txt)
                        }

                        R.id.chipFavoriteSnacksBanana -> {
                            val txt = favoriteSnacksChipGroup.findViewById<Chip>(it).text.toString()
                            list.add(txt)
                        }

                        R.id.chipFavoriteSnacksKorApple -> {
                            val txt = favoriteSnacksChipGroup.findViewById<Chip>(it).text.toString()
                            list.add(txt)
                        }
                    }
                }
            }


            btnSave.setOnClickListener {
                var name = textFieldName.editText?.text.toString()
                // 나이를 입력받고 변환 처리
                var age = textFieldAge.editText?.text.toString().toIntOrNull()
                    ?: 0 // 입력값이 null일 경우 기본값 0으로 설정


                val dataIntent = Intent()
                dataIntent.putExtra("type", type)
                dataIntent.putExtra("name", name)
                dataIntent.putExtra("age", age)
                dataIntent.putExtra("gender", gender)
                dataIntent.putExtra("favoriteSnacks", list)

                Log.d(
                    "InputDataActivity",
                    "Type: $type, Name: $name, Age: $age, Gender: $gender, Snacks: $list"
                )

                setResult(RESULT_OK, dataIntent)
                finish()
                false
            }

        }
    }

}