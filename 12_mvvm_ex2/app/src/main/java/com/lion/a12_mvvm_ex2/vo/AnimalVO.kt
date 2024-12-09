package com.lion.a12_mvvm_ex2.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AnimalTable")
data class AnimalVO(
    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0,
    var type: Int = 0,
    var name: String = "",
    var age: Int = 0,
    var gender: Int = 0,
) {

}