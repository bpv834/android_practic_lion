package com.lion.a055_ex_ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lion.a055_ex_ui.databinding.FragmentShowBinding

class ShowFragment : Fragment() {
    lateinit var dataModel : DataModel
    lateinit var mainActivity: MainActivity
    lateinit var fragmentShowBinding: FragmentShowBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentShowBinding = FragmentShowBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        dataModel = arguments?.getSerializable("dataModel") as DataModel

        //툴바 세팅
        settingToolbar()

        //데이터 세팅
        settingData()
        Log.d("test200", "${dataModel}")
        return fragmentShowBinding.root
    }

    // 툴바를 구성하는 메서드
    fun settingToolbar() {
        fragmentShowBinding.materialToolbarShow.apply {
            // 타이틀
            title = "Show"
            // 좌측 네비게이션 버튼
            setNavigationIcon(R.drawable.ic_launcher_foreground)
            // 좌측 네비게이션 버튼을 누르면 NavigationView가 나타나게 한다.
            setNavigationOnClickListener {
                mainActivity.removeFragment(FragmentName.SHOW_FRAGMENT)
            }

        }
    }

    // 번들로 받은 객체를 통해 데이터 입력 메서드
    fun settingData() {
        fragmentShowBinding.apply {
            textViewTypeShow.text = dataModel.type
            textViewNameShow.text = dataModel.name
            textViewAgeShow.text = dataModel.age.toString()
        }
    }

}