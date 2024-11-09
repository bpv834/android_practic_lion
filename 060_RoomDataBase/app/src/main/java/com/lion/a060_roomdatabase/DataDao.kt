package com.lion.a060_roomdatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
// Data Access Object
@Dao
interface DataDao {
    // insert
    // autoGenerate 가 설정되어 있는 프로퍼티의 값은 1부터 1씩 증가하는 값으로
    // 저장이되고 나머지 컬럼의 값은 dataModel이 가진 프로퍼티의 값이 지정되어 저장된다
    @Insert
    fun insertData(dataModel: DataModel)

    // 쿼리문을 자유롭게 작성하고 싶을때 사용한다.
    // select문을 사용하거나 insert, update, delete 할 때 쿼리문을 직접 작성하고
    // 싶을 때 사용한다.
    // 쿼리문을 실행해서 가져온 데이터를
    // 각 행당 하나의 DataModel 객체를 생성하고 그 객체에 데이터를 담아준다.
    // 이 때 컬럼명과 동일한 이름의 프로퍼티에 자동으로 넣어준다.
    // 이렇게 만들어진 객체들을 List에 담아 반환해준다.
    @Query("select * from TestTable")
    fun selectDataAll() : List<DataModel>

    // 쿼리문을 작성할 때 매개변수로 들어오는 값을 대입해주고 싶다면
    // :변수명 형태로 작성해준다.
    // 만약 반환타입이 List가 아닌 경우에는 제일 첫 번째 행의 데이터만 객체에 담아
    // 반환해준다.
    @Query("select * from TestTable where idx = :idx")
    fun selectDataOne(idx:Int) : DataModel

    // 수정
    // 매개변수로 전달된 객체의 프로퍼티 중에 autoGenerate가 설정되어 있는
    // 프로퍼티를 이용해 조건식이 만들어진다
    @Update
    fun updateData(dataModel: DataModel)

    // 삭제
    // 매개변수로 전달된 객체의 프로퍼티 중에 autoGenerate가 설정되어 있는
    // 프로퍼티를 이용해 조건식이 만들어진다
    @Delete
    fun deleteData(dataModel: DataModel)
}