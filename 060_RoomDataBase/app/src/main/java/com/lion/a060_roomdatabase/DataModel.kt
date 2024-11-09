package com.lion.a060_roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

// 객체와 연동될 테이블의 이름을 지정해준다.
// 테이블의 이 객체의 구조와 동일한 구조로 생성된다.
// 컬럼의 이름은 프로퍼티의 이름과 동일하게 생성된다.
// 나중에 데이터를 저장하거나 가져오는 작업을 하게되면
// 프로퍼티의 이름과 동일한 이름의 컬럼으로 매칭하여 작업을 수행해준다.
@Entity(tableName = "TestTable")
data class DataModel(
    // primary key 컬럼이나 autoincrment 컬럼으로 지정한다.
    // 1부터 1씩 증가되는 값이 자동으로 들어간다.
    @PrimaryKey(autoGenerate = true)
    var idx:Int = 0,
    var textData:String = "",
    var intData:Int = 0,
    var doubleData:Double = 0.0
){}