package com.lion.a061ex_roomdatabase.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StudentTable")
data class StudentVO(
    @PrimaryKey(autoGenerate = true)
    var studentIdx:Int = 0,
    var studentName:String = "",
    var studentType:Int = 0,
    var studentAge:Int = 0
)