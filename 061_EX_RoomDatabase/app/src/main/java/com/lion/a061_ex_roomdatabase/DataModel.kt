package com.lion.a061_ex_roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "StudentTable")
class DataModel(
    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0,
    var type: String = "",
    var name: String = "",
    var age: Int = 0,
    )