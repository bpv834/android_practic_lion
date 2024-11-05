package com.example.a042_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a042_fragment.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    lateinit var fragmentFirstBinding: FragmentFirstBinding

    // Fragment 객체 생성시 호출되는 메서드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Fragment를 통해 보여줄 화면 UI 요소들을 만들기 위해 호출하는 메서드
    // 이 메서드가 반환하는 View를 Fragment를 통해 관리할 화면 객체로 지정한다.
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentFirstBinding = FragmentFirstBinding.inflate(inflater)
        return fragmentFirstBinding.root
    }
}