package com.lion.a12_mvvm_student.service

import android.content.Context
import com.lion.a12_mvvm_student.model.StudentModel
import com.lion.a12_mvvm_student.repository.StudentRepository
import com.lion.a12_mvvm_student.viewmodel.InputViewModel
import com.lion.a12_mvvm_student.viewmodel.ShowViewModel

class StudentService {

    companion object{
        // 사용자가 입력한 정보를 Model에 담고 저장하는 메서드를 호출한다.
        fun addStudentInfo(context: Context, inputViewModel: InputViewModel){
            // 사용자가 입력한 데이터를 Model에 담아준다.
            val studentModel = StudentModel(
                studentName = inputViewModel.editTextInputName.value.toString(),
                studentAge = inputViewModel.editTextInputAge.value.toString().toInt(),
                studentKorean = inputViewModel.editTextInputKorean.value.toString().toInt(),
                studentEnglish = inputViewModel.editTextInputEnglish.value.toString().toInt(),
                studentMath = inputViewModel.editTextInputMath.value.toString().toInt()
            )
            // 저장하는 메서드를 호출한다.
            StudentRepository.insertStudentInfo(context, studentModel)
        }

        // 모든 학생의 정보를 받아와 반환
        fun gettingStudentAll(context: Context): MutableList<StudentModel>{
            val studentList =StudentRepository.selectStudentAll(context)
            return studentList
        }
        // 학생 정보를 받아와 출력한다.
        fun showStudentInfoByStudentIdx(context: Context, studentIdx:Int):StudentModel{
            val studentModel = StudentRepository.selectStudentByStudentIdx(context, studentIdx)
            return studentModel
        }
    }
}