package com.example.a040ex_animalmanager.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import com.example.a040ex_animalmanager.FragmentName
import com.example.a040ex_animalmanager.MainActivity
import com.example.a040ex_animalmanager.R
import com.example.a040ex_animalmanager.databinding.FragmentShowBinding


class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding
    var idx = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentShowBinding = FragmentShowBinding.inflate(inflater)

        // arguments가 null인지 체크
        val args = arguments
        if (args != null) {
            idx = args.getInt("position", -1)
            Log.d("test200", "idx : $idx")
            settingDataByArguments()
        } else {
            Log.e("ShowFragmentError", "Arguments are null!")
            // Fragment 종료 시 기본적으로 빈 뷰 반환
            return fragmentShowBinding.root
        }
        settingBtnGoHome()
        settingBtnDeleteData()

        return fragmentShowBinding.root
    }

    fun settingDataByArguments() {
        fragmentShowBinding.apply {
            val a1 = activity as MainActivity
            val idx = arguments?.getInt("position", -1)!!
            if (idx != -1 && idx < a1.dataList.size) {
                val age = a1.dataList[idx].age
                val list = a1.dataList[idx].favoriteSnack
                txtTypeShowData.text = a1.dataList[idx].type
                txtNameShowData.text = a1.dataList[idx].name
                txtAgeShowData.text = age.toString() // 정수형 기본값
                txtGenderShowData.text = a1.dataList[idx].gender
                list.forEach {
                    Log.d("test300:", "$it")
                    txtFavoriteSnacksShowData.append("$it ")
                }
                txtIdx.text = idx.toString()
            } else {
                Log.e("FragmentError", "Invalid index or arguments.")
            }
        }
    }


    fun settingBtnGoHome() {
        fragmentShowBinding.apply {
            btnGoHome.setOnClickListener {
                val a1 = activity as MainActivity
                a1.removeFragment(FragmentName.SHOW_FRAGMENT)
            }
        }
    }

    fun settingBtnDeleteData() {
        fragmentShowBinding.apply {
            btnDeleteData.setOnClickListener {
                val a1 = activity as MainActivity
                val idx = arguments?.getInt("position")
                a1.dataList.removeAt(idx!!)
                a1.removeFragment(FragmentName.SHOW_FRAGMENT)

            }
        }
    }

}