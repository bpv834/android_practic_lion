package com.example.a042ex_fragment1.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a042ex_fragment1.MainActivity
import com.example.a042ex_fragment1.R
import com.example.a042ex_fragment1.databinding.FragmentShowBinding

class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentShowBinding = FragmentShowBinding.inflate(inflater)
        settingTextView()
        return fragmentShowBinding.root
    }

    // TextView 구성
    fun settingTextView() {
        fragmentShowBinding.apply {
            if (arguments != null) {
                // 순서값을 가져온다.
                val position = arguments?.getInt("position")!!
                // TextView에 값을 넣어준다.
                val a1 = activity as MainActivity
                textViewShowName.text = a1.dataList[position].name
                textViewShowAge.text = a1.dataList[position].age.toString()
                textViewShowKorean.text = a1.dataList[position].korean.toString()
            }
        }
    }
}