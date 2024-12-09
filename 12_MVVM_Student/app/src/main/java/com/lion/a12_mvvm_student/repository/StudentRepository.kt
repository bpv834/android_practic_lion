package com.lion.a12_mvvm_student.repository

import android.content.Context
import com.lion.a12_mvvm_student.database.StudentDataBase
import com.lion.a12_mvvm_student.model.StudentModel
import com.lion.a12_mvvm_student.vo.StudentVO

class StudentRepository {

 companion object{
     // 학생 정보 저장
     fun insertStudentInfo(context: Context, studentModel: StudentModel) {
         val studentVO = StudentVO(studentModel)
         val studentDataBase = StudentDataBase.getInstance(context)
         studentDataBase?.studentDao()?.insertStudentInfo(studentVO)
     }

     // 학생 정보 전체를 가져온다.
     fun selectStudentAll(context: Context) : MutableList<StudentModel>{
         // 학생 데이터를 가져온다.
         val studentDataBase = StudentDataBase.getInstance(context)
         val voList = studentDataBase?.studentDao()?.selectStudentAll()
         // 학생 데이터를 담을 리스트
         val studentList = mutableListOf<StudentModel>()
         // 학생의 수 만큼 반복한다.
         voList?.forEach{
             val studentModel = StudentModel(it)
             studentList.add(studentModel)
         }

         return studentList
     }

     // 학생 번호를 통해 학생 정보 한명의 데이터를 가져온다.
     fun selectStudentByStudentIdx(context: Context, studentIdx:Int) : StudentModel{
         val studentDataBase = StudentDataBase.getInstance(context)
         val studentVO = studentDataBase?.studentDao()?.selectStudentByStudentIdx(studentIdx)
         val studentModel = StudentModel(studentVO!!)
         return studentModel
     }

 }
}