package com.lion.a07_studentmanager.repository

import android.content.Context
import com.lion.a07_studentmanager.database.PlannerVO
import com.lion.a07_studentmanager.database.StudentDataBase

class PlannerRepository {
    companion object {

        // 데이터를 저장하는 메서드
        fun insertPlannerData(context: Context, plannerVO: PlannerVO){
            val studentDataBase = StudentDataBase.getInstance(context)
            studentDataBase?.plannerDao()?.insertPlannerData(plannerVO)
        }

        // 학사 일정 데이터 전체를 가져오는 메서드
        fun selectPlannerDataAll(context:Context):List<PlannerVO>{
            val studentDataBase = StudentDataBase.getInstance(context)
            val plannerList = studentDataBase?.plannerDao()?.selectPlannerDataAll()
            return plannerList!!
        }

        // 학사 일정을 삭제하는 메서드
        fun deletePlannerData(context: Context, plannerVO: PlannerVO){
            val studentDataBase = StudentDataBase.getInstance(context)
            studentDataBase?.plannerDao()?.deletePlannerData(plannerVO)
        }

        // 특정 학사 일정 하나를 가져오는 메서드
        fun selectPlannerDataOneByPlannerIdx(context: Context, plannerIdx:Int) : PlannerVO{
            val studentDataBase = StudentDataBase.getInstance(context)
            val plannerVO = studentDataBase?.plannerDao()?.selectPlannerDataOneByPlannerIdx(plannerIdx)
            return plannerVO!!
        }
    }
}