package com.lion.a12_mvvm_student.model

import com.lion.a12_mvvm_student.vo.StudentVO

data class StudentModel(
    var studentIdx:Int = 0,
    var studentName:String = "",
    var studentAge:Int = 0,
    var studentKorean:Int = 0,
    var studentEnglish:Int = 0,
    var studentMath:Int = 0
){
    constructor(studentVO: StudentVO):this(
        studentVO.studentIdx,
        studentVO.studentName,
        studentVO.studentAge,
        studentVO.studentKorean,
        studentVO.studentEnglish,
        studentVO.studentMath,
    )
}