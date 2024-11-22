package com.lion.a061ex_roomdatabase.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lion.a061ex_roomdatabase.MainActivity
import com.lion.a061ex_roomdatabase.R
import com.lion.a061ex_roomdatabase.databinding.FragmentShowBinding
import com.lion.a061ex_roomdatabase.repository.StudentRepository
import com.lion.a061ex_roomdatabase.util.FragmentName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ShowFragment : Fragment() {

    lateinit var fragmentShowBinding: FragmentShowBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentShowBinding = FragmentShowBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바 설정 메서드 호출
        settingToolbar()
        // 데이터를 가져와 출력한다.
        settingTextView()

        return fragmentShowBinding.root
    }

    // 툴바 설정 메서드
    fun settingToolbar(){
        fragmentShowBinding.apply {
            // 타이틀
            toolbarShow.title = "학생 정보 보기"
            // 네비게이션
            toolbarShow.setNavigationIcon(R.drawable.arrow_back_24px)
            toolbarShow.setNavigationOnClickListener {
                mainActivity.removeFragment(FragmentName.SHOW_FRAGMENT)
            }
            // 메뉴
            toolbarShow.inflateMenu(R.menu.show_toolbar_menu)
            toolbarShow.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.show_toolbar_menu_edit -> {
                        // 학생 번호를 담아준다.
                        val dataBundle = Bundle()
                        dataBundle.putInt("studentIdx", arguments?.getInt("studentIdx")!!)
                        // ModifyFragment로 이동한다.
                        mainActivity.replaceFragment(FragmentName.MODIFY_FRAGMENT, true, dataBundle)
                    }
                    R.id.show_toolbar_menu_delete -> {
                        // mainActivity.removeFragment(FragmentName.SHOW_FRAGMENT)
                        // 삭제를 위한 다이얼로그를 띄운다.
                        deleteStudentInfo()
                    }
                }
                true
            }
        }
    }

    // TextView에 값을 설정하는 메서드
    fun settingTextView(){
        // 만일의 경우를 위해 TextView들을 초기화 해준다.
        fragmentShowBinding.textViewShowType.text = ""
        fragmentShowBinding.textViewShowName.text = ""
        fragmentShowBinding.textViewShowAge.text = ""

        // 학생 번호를 추출한다.
        val studentIdx = arguments?.getInt("studentIdx")
        // 학생 데이터를 가져와 출력한다.
        CoroutineScope(Dispatchers.Main).launch {
            val work1 = async(Dispatchers.IO){
                StudentRepository.selectStudentInfoByStudentIdx(mainActivity, studentIdx!!)
            }
            val studentViewModel = work1.await()

            fragmentShowBinding.textViewShowType.text = studentViewModel.studentType.str
            fragmentShowBinding.textViewShowName.text = studentViewModel.studentName
            fragmentShowBinding.textViewShowAge.text = studentViewModel.studentAge.toString()
        }
    }

    // 삭제처리 메서드
    fun deleteStudentInfo(){
        // 다이얼로그를 띄워주다.
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
        materialAlertDialogBuilder.setTitle("삭제")
        materialAlertDialogBuilder.setMessage("삭제를 할 경우 복원이 불가능합니다")
        materialAlertDialogBuilder.setNeutralButton("취소", null)
        materialAlertDialogBuilder.setPositiveButton("삭제"){ dialogInterface: DialogInterface, i: Int ->
            CoroutineScope(Dispatchers.Main).launch {
                val work1 = async(Dispatchers.IO){
                    val studentIdx = arguments?.getInt("studentIdx")
                    StudentRepository.deleteStudentInfoByStudentIdx(mainActivity, studentIdx!!)
                }
                work1.join()
                mainActivity.removeFragment(FragmentName.SHOW_FRAGMENT)
            }
        }
        materialAlertDialogBuilder.show()
    }
}











