package com.lion.a07_studentmanager.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlannerDao {

    // 학사 일정 데이터를 저장한다.
    @Insert
    fun insertPlannerData(plannerVO: PlannerVO)

    // 학사 일정 데이터를 가져온다.
    @Query("""
        select * from PlannerTable
        order by plannerIdx desc
    """)
    fun selectPlannerDataAll():List<PlannerVO>

    // 학사 일정을 삭제한다.
    @Delete
    fun deletePlannerData(plannerVO: PlannerVO)

    // 특정 학사 일정 데이터를 가져온다.
    @Query("""
        select * from PlannerTable
        where plannerIdx = :plannerIdx
    """)
    fun selectPlannerDataOneByPlannerIdx(plannerIdx:Int) : PlannerVO
}