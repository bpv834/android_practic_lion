package com.lion.a12_mvvm_student.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lion.a12_mvvm_student.vo.StudentVO

@Dao
interface StudentDao {

    @Insert
    fun insertStudentInfo(studentVO: StudentVO)

    // 학생 정보 전체를 가져온다.
    @Query("""
        select * from StudentTable
        order by studentIdx desc
    """)
    fun selectStudentAll() : List<StudentVO>

    // 학생 번호를 통해 학생 정보 한명의 데이터를 가져온다.
    @Query("""
        select * from StudentTable
        where studentIdx = :studentIdx
    """)
    fun selectStudentByStudentIdx(studentIdx:Int) : StudentVO
}