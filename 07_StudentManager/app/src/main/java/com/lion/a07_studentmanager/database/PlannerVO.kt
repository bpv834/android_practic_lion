package com.lion.a07_studentmanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlannerTable")
class PlannerVO (
    // 학사 일정 번호
    @PrimaryKey(autoGenerate = true)
    var plannerIdx:Int = 0,
    // 학사 일정 내용
    var plannerContent:String = "",
    // 년
    var plannerYear:Int = 0,
    // 월
    var plannerMonth:Int = 0,
    // 일
    var plannerDay:Int = 0
)