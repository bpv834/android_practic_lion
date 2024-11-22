package com.lion.a061ex_roomdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lion.a061ex_roomdatabase.MainActivity
import com.lion.a061ex_roomdatabase.R
import com.lion.a061ex_roomdatabase.databinding.FragmentInputBinding
import com.lion.a061ex_roomdatabase.repository.StudentRepository
import com.lion.a061ex_roomdatabase.util.FragmentName
import com.lion.a061ex_roomdatabase.util.StudentType
import com.lion.a061ex_roomdatabase.viewmodel.StudentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class InputFragment : Fragment() {

    lateinit var fragmentInputBinding: FragmentInputBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentInputBinding = FragmentInputBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 툴바를 구성하는 메서드를 호출한다.
        settingToolbar()

        return fragmentInputBinding.root
    }

    // Toolbar를 설정하는 메서드
    fun settingToolbar(){
        fragmentInputBinding.apply {
            // 타이틀
            toolbarInput.title = "학생 정보 입력"
            // 네비게이션 아이콘
            toolbarInput.setNavigationIcon(R.drawable.arrow_back_24px)
            toolbarInput.setNavigationOnClickListener {
                // 이전 화면으로 돌아간다.
                mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
            }
            // 메뉴
            toolbarInput.inflateMenu(R.menu.input_toolbar_menu)
            toolbarInput.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.input_toolbar_menu_done -> {
                        // 사용자가 입력한 데이터를 가져온다.
                        // 운동부
                        val studentType = when(toggleGroupType.checkedButtonId){
                            // 야구부
                            R.id.buttonTypeBaseBall -> StudentType.STUDENT_TYPE_BASEBALL
                            // 농구부
                            R.id.buttonTypeBasketBall -> StudentType.STUDENT_TYPE_BASKETBALL
                            // 축구부
                            else -> StudentType.STUDENT_TYPE_SOCCER
                        }
                        // 이름
                        val studentName = textFieldInputName.editText?.text.toString()
                        // 나이
                        val studentAge = textFieldInputAge.editText?.text.toString().toInt()
                        // 객체에 담는다.
                        val studentViewModel = StudentViewModel(0, studentType, studentName, studentAge)

                        // 데이터를 저장하는 메서드를 코루틴으로 운영한다.
                        CoroutineScope(Dispatchers.Main).launch {
                            // 저장작업이 끝날때까지 대기한다.
                            val work1 = async(Dispatchers.IO){
                                // 저장한다.
                                StudentRepository.insertStudentInfo(mainActivity, studentViewModel)
                            }
                            work1.join()
                            // 저장작업이 모두 끝나면 이전 화면으로 돌아간다.
                            mainActivity.removeFragment(FragmentName.INPUT_FRAGMENT)
                        }
                    }
                }
                true
            }
        }
    }
}