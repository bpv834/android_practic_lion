package com.example.a040ex_animalmanager.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a040ex_animalmanager.DataModel
import com.example.a040ex_animalmanager.FragmentName
import com.example.a040ex_animalmanager.MainActivity
import com.example.a040ex_animalmanager.R
import com.example.a040ex_animalmanager.databinding.FragmentInputBinding
import com.google.android.material.chip.Chip

class InputFragment : Fragment() {
    lateinit var fragmentInputBinding : FragmentInputBinding

    // 클래스 프로퍼티로 선언
    var type = ""
    var gender = ""
    val snacksList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentInputBinding = FragmentInputBinding.inflate(inflater)

 /*       // 칩 리스너를 초기화
        setupChipListeners()*/

        // 버튼 설정
        settingButtonInputSubmit()
        // Inflate the layout for this fragment
        return fragmentInputBinding.root
    }

    // 칩 리스너 설정 메서드
    private fun setupChipListeners() {
        fragmentInputBinding.apply {
            typeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    when (checkedIds[0]) {
                        R.id.chipTypeDog -> type = "강아지"
                        R.id.chipTypeCat -> type = "고양이"
                        R.id.chipTypeParrot -> type = "앵무새"
                    }
                }
            }

            genderChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                if (checkedIds.isNotEmpty()) {
                    when (checkedIds[0]) {
                        R.id.chipGenderMale -> gender = "남자"
                        R.id.chipGenderFemale -> gender = "여자"
                    }
                }
            }

            favoriteSnacksChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                snacksList.clear()

                checkedIds.forEach {
                    val chipText = favoriteSnacksChipGroup.findViewById<Chip>(it).text.toString()
                    Log.d("test1000", "과일 : $chipText")
                    snacksList.add(chipText)
                }
            }
        }
    }

    fun inputDone() {
        fragmentInputBinding.apply {

            when(typeChipGroup.checkedChipId){
                R.id.chipTypeDog -> {
                    type = "강아지"
                }
                R.id.chipTypeCat->{
                    type = "고양이"
                }
                R.id.chipTypeParrot->{
                    type = "앵무새"
                }
            }

            when(genderChipGroup.checkedChipId){
                R.id.chipGenderMale->{
                    gender = "남자"
                }
                R.id.chipGenderFemale->{
                    gender = "여자"
                }
            }

            favoriteSnacksChipGroup.checkedChipIds.forEach {
                when(it){
                    R.id.chipFavoriteSnacksApple->{
                        snacksList.add("사과")
                    }
                    R.id.chipFavoriteSnacksBanana->{
                        snacksList.add("바나나")
                    }

                    R.id.chipFavoriteSnacksKorApple->{
                        snacksList.add("대추")
                    }
                }
            }

            val name = textFieldInputName.editText?.text.toString()
            val age = textFieldInputAge.editText?.text.toString().toInt()

            val a1 = activity as MainActivity
            val model = DataModel(type, name, age, gender, snacksList)
            a1.dataList.add(model)

            a1.removeFragment(FragmentName.INPUT_FRAGMENT)

            Log.d("test500","${a1.dataList}")



        }
    }
    fun settingButtonInputSubmit() {
        fragmentInputBinding.apply {
            buttonInputSubmit.setOnClickListener {
                inputDone()
            }
        }
    }
}


