package com.lion.a12_mvvm_student.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lion.a12_mvvm_student.model.StudentModel

@Entity(tableName = "StudentTable")
data class StudentVO (
    @PrimaryKey(autoGenerate = true)
    var studentIdx:Int = 0,
    var studentName:String = "",
    var studentAge:Int = 0,
    var studentKorean:Int = 0,
    var studentEnglish:Int = 0,
    var studentMath:Int = 0
){
    constructor(studentModel: StudentModel):this(
        studentModel.studentIdx,
        studentModel.studentName,
        studentModel.studentAge,
        studentModel.studentKorean,
        studentModel.studentEnglish,
        studentModel.studentMath,
    )
}