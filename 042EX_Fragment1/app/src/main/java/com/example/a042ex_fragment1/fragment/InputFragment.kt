package com.example.a042ex_fragment1.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a042ex_fragment1.DataModel
import com.example.a042ex_fragment1.FragmentName
import com.example.a042ex_fragment1.MainActivity
import com.example.a042ex_fragment1.R
import com.example.a042ex_fragment1.databinding.FragmentInputBinding

class InputFragment : Fragment() {
    lateinit var fragmentInputBinding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentInputBinding = FragmentInputBinding.inflate(inflater)

        // 입력 완료 버튼
        settingButtonInputSubmit()
        // 입력 요소
        settingTextFieldInput()

        return fragmentInputBinding.root
    }


    // 입력 요소 셋팅
    fun settingTextFieldInput(){
        fragmentInputBinding.apply {
            // 마지막 입력 요소에서 Enter를 눌렀을 경우
            textFieldINputKorean.editText?.setOnEditorActionListener { v, actionId, event ->
                inputDone()
                false
            }
        }
    }

    // 입력 완료 버튼
    fun settingButtonInputSubmit(){
        fragmentInputBinding.apply {
            buttonInputSubmit.setOnClickListener {
                inputDone()
            }
        }
    }


    // 입력 완료 처리 메서드
    fun inputDone(){
        fragmentInputBinding.apply {
            // 입력한 데이터를 가져온다.
            val name = textFieldInputName.editText?.text.toString()
            val age = textFieldInputAge.editText?.text.toString().toInt()
            val korean = textFieldINputKorean.editText?.text.toString().toInt()
            // 객체에 담는다
            val dataModel = DataModel(name, age, korean)
            // 리스트에 담는다.
            val a1 = activity as MainActivity
            a1.dataList.add(dataModel)

            Log.d("test100", a1.dataList.toString())
            // 이전으로 돌아간다.
            a1.removeFragment(FragmentName.INPUT_FRAGMENT)
        }
    }

}