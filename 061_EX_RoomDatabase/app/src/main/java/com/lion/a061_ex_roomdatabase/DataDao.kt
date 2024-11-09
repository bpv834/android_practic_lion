package com.lion.a061_ex_roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

// @Dao 인터페이스는 데이터베이스의 CRUD(생성, 조회, 갱신, 삭제) 작업을 위한 메서드를 정의하고,
// Room이 자동으로 구현을 제공하여 다음과 같은 기능이 추가됩니다.
@Dao
interface DataDao {
    // 학생 정보 저장
    @Insert
    fun insertStudentData(dataModel: DataModel)

    // 학생의 모든 정보를 가져온다.
    @Query("select * from StudentTable")
    fun selectStudentDataAll() : List<DataModel>

    // 특정 학생의 정보를 가져온다.
    @Query("select * from StudentTable where idx = :idx")
    fun selectStudentDataFilter(idx : Int) : DataModel
}