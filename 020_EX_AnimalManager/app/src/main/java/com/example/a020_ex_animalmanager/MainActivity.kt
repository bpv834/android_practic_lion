package com.example.a020_ex_animalmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach
import com.example.a020_ex_animalmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var animals = mutableListOf<AnimalClass>()
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
        settingButtonSave()
        settingButtonShow()
    }
    // 동물 정보 출력
    fun settingButtonShow(){
        activityMainBinding.apply {
            showTxtView.text = ""

            // 동물의 수 만큼 반복한다.
            animals.forEach {
                showTxtView.append("동물 종류 : ${it.type}\n")
                showTxtView.append("동물 이름 : ${it.name}\n")
                showTxtView.append("동물 나이 : ${it.age}\n")
                showTxtView.append("동물 성별 : ${it.gender.str}\n")
                showTxtView.append("최소 몸무게 : ${it.minWeight}\n")
                showTxtView.append("최대 몸무게 : ${it.maxWeight}\n")
                it.favoriteSnack.forEach { foodType ->
                    showTxtView.append("좋아하는 간식 : ${foodType.str}\n")
                }
                showTxtView.append("\n")
            }
        }
    }



    // 저장 버튼 구현
    fun settingButtonSave(){
        activityMainBinding.apply {
            btnInputInfo.setOnClickListener {

                // 동물 타입을 담는다
                val animalType = when(typeChipGroup.checkedChipId){
                    R.id.chipDog -> AnimalType.TYPE_DOG
                    R.id.chipCat -> AnimalType.TYPE_CAT
                    else -> AnimalType.TYPE_PARROT
                }

                // 동물 이름
                val animalName = txtInputName.editText?.text.toString()
                // 동물 나이
                val animalAge = slider.value.toInt()

                // 성별
                val animalGender = when(genderRadioGroup.checkedRadioButtonId){
                    R.id.radioBtnM -> AnimalGender.GENDER_MALE
                    else -> AnimalGender.GENDER_FEMALE
                }

                //몸무게 범위
                val minWeight = rangeSlider.values[0].toInt()
                val maxWeight = rangeSlider.values[1].toInt()

                // 좋아하는 간식
                val animalFood = mutableListOf<AnimalFood>()
                snackChipGroup.checkedChipIds.forEach {
                    when(it){
                        R.id.chipApple -> animalFood.add(AnimalFood.FOOD_APPLE)
                        R.id.chipBanana -> animalFood.add(AnimalFood.FOOD_BANANA)
                        R.id.chipKorApple -> animalFood.add(AnimalFood.FOOD_JUJUBE)
                    }
                }

                // 동물 객체를 생성한다
                val animalClass = AnimalClass(animalName,animalAge,animalType.toString(),animalGender,minWeight, maxWeight, animalFood)
                animals.add(animalClass)

                // 입력 요소들 초기화
                typeChipGroup.check(R.id.chipDog)
                txtInputName.editText?.setText("")
                slider.value = 0.0f
                genderRadioGroup.check(R.id.radioBtnM)
                rangeSlider.values = mutableListOf(10.0f, 30.0f)
                chipApple.isChecked = false
                chipBanana.isChecked = false
                chipKorApple.isChecked = false
            }
        }
    }
}




/*

        activityMainBinding.apply {
            btnInputInfo.setOnClickListener {
                val name = txtInputName.editText?.text.toString()
                var gender = ""
                var type = ""
                var favoriteSnack = ""
                val  age = slider.value.toInt()
                val minWeight = rangeSlider.values[0].toInt()
                val maxWeight = rangeSlider.values[1].toInt()
                when (genderRadioGroup.checkedRadioButtonId) {
                    R.id.radioBtnM -> gender = "남"
                    R.id.radioBtnFe -> gender = "여"
                }
                when (typeChipGroup.checkedChipId) {
                    R.id.chipDog -> type = "강아지"
                    R.id.chipCat -> type = "고양이"
                    R.id.chipParrot -> type = "앵무새"
                }
                snackChipGroup.checkedChipIds.forEach {
                    when (it) {
                        R.id.chipApple -> favoriteSnack += "사과 "
                        R.id.chipBanana -> favoriteSnack += "바나나"
                        R.id.chipKorApple -> favoriteSnack += "대추"
                    }
                }
                val animalClass = AnimalClass(name,age,type,gender,minWeight, maxWeight, favoriteSnack)
                animals.add(animalClass)
            }*/
      /*      btnShowInfo.setOnClickListener {
                showTxtView.text = ""
                animals.forEach {
                    showTxtView.text = showTxtView.text.toString() + "${it.name}, ${it.age}, ${it.gender}, ${it.type}, ${it.favoriteSnack}"
                }
            }
        }
    }*/
}