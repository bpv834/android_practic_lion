package com.lion.a061ex_roomdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lion.a061ex_roomdatabase.vo.StudentVO

@Dao
interface StudentDAO {

    // 학생 정보 저장
    @Insert
    fun insertStudentData(studentVO: StudentVO)

    // 학생 정보를 가져오는 메서드
    @Query("""
        select * from StudentTable 
        order by studentIdx desc""")
    fun selectStudentDataAll() : List<StudentVO>

    // 학생 한명의 정보를 가져오는 메서드
    @Query("""
        select * from StudentTable
        where studentIdx = :studentIdx
    """)
    fun selectStudentDataByStudentIdx(studentIdx:Int) : StudentVO

    // 학생 한명의 정보를 삭제하는 메서드
    @Delete
    fun deleteStudentData(studentVO: StudentVO)

//    @Query("delete from StudentTable where studentIdx = :studentIdx")
//    fun deleteStudentData(studentIdx:Int)

    // 학생 한명의 정보를 수정하는 메서드
    @Update
    fun updateStudentData(studentVO: StudentVO)
}