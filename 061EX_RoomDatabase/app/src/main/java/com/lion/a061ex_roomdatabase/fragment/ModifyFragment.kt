package com.lion.a061ex_roomdatabase.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.a061ex_roomdatabase.MainActivity
import com.lion.a061ex_roomdatabase.R
import com.lion.a061ex_roomdatabase.dao.StudentDatabase
import com.lion.a061ex_roomdatabase.databinding.FragmentModifyBinding
import com.lion.a061ex_roomdatabase.repository.StudentRepository
import com.lion.a061ex_roomdatabase.util.FragmentName
import com.lion.a061ex_roomdatabase.util.StudentType
import com.lion.a061ex_roomdatabase.viewmodel.StudentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ModifyFragment : Fragment() {

    lateinit var fragmentModifyBinding: FragmentModifyBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentModifyBinding = FragmentModifyBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // Toolbar 설정 메서드를 호출한다.
        settingToolbar()
        // 입력 요소들 초기 설정
        settingInput()

        return fragmentModifyBinding.root
    }

    // Toolbar 설정 메서드
    fun settingToolbar(){
        fragmentModifyBinding.apply {
            // 타이틀
            toolbarModify.title = "학생 정보 수정"
            // 네비게이션
            toolbarModify.setNavigationIcon(R.drawable.arrow_back_24px)
            toolbarModify.setNavigationOnClickListener {
                mainActivity.removeFragment(FragmentName.MODIFY_FRAGMENT)
            }
            // 메뉴
            toolbarModify.inflateMenu(R.menu.modify_toolbar_menu)
            toolbarModify.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.modify_toolbar_menu_done ->{
                        // mainActivity.removeFragment(FragmentName.MODIFY_FRAGMENT)
                        modifyDone()
                    }
                }
                true
            }
        }
    }

    // 입력 요소들 초기 설정
    fun settingInput(){
        fragmentModifyBinding.apply {
            // 학생 번호를 가져온다.
            val studentIdx = arguments?.getInt("studentIdx")!!
            // 학생 데이터를 가져온다.
            CoroutineScope(Dispatchers.Main).launch {
                val work1 = async(Dispatchers.IO){
                    StudentRepository.selectStudentInfoByStudentIdx(mainActivity, studentIdx)
                }
                val studentViewModel = work1.await()

                when(studentViewModel.studentType){
                    StudentType.STUDENT_TYPE_BASEBALL -> {
                        toggleGroupModifyType.check(R.id.buttonModifyTypeBaseBall)
                    }
                    StudentType.STUDENT_TYPE_BASKETBALL -> {
                        toggleGroupModifyType.check(R.id.buttonModifyTypeBasketBall)
                    }
                    StudentType.STUDENT_TYPE_SOCCER -> {
                        toggleGroupModifyType.check(R.id.buttonModifyTypeSoccer)
                    }
                }
                textFieldModifyName.editText?.setText(studentViewModel.studentName)
                textFieldModifyAge.editText?.setText(studentViewModel.studentAge.toString())
            }
        }
    }

    // 수정 처리 메서드
    fun modifyDone(){
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
        materialAlertDialogBuilder.setTitle("수정")
        materialAlertDialogBuilder.setMessage("이전 데이터로 복원할 수 없습니다")
        materialAlertDialogBuilder.setNeutralButton("취소", null)
        materialAlertDialogBuilder.setPositiveButton("수정"){ dialogInterface: DialogInterface, i: Int ->
            // 수정할 데이터
            val studentIdx = arguments?.getInt("studentIdx")!!
            val studentType = when(fragmentModifyBinding.toggleGroupModifyType.checkedButtonId){
                R.id.buttonModifyTypeBaseBall -> StudentType.STUDENT_TYPE_BASEBALL
                R.id.buttonModifyTypeBasketBall -> StudentType.STUDENT_TYPE_BASKETBALL
                else -> StudentType.STUDENT_TYPE_BASKETBALL
            }
            val studentName = fragmentModifyBinding.textFieldModifyName.editText?.text.toString()
            val studentAge = fragmentModifyBinding.textFieldModifyAge.editText?.text.toString().toInt()

            val studentViewModel = StudentViewModel(studentIdx, studentType, studentName, studentAge)

            CoroutineScope(Dispatchers.Main).launch {
                val work1 = async(Dispatchers.IO){
                    StudentRepository.updateStudentInfo(mainActivity, studentViewModel)
                }
                work1.join()
                mainActivity.removeFragment(FragmentName.MODIFY_FRAGMENT)
            }
        }
        materialAlertDialogBuilder.show()
    }
}








