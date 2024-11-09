package com.lion.a059_sqlitedatabase2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// 부모의 생성자
// 두 번째 : 데이터 베이스 파일 이름. 없으면 생성해준다.
// 세 번째 : null 값에 대한 처리 방식 설정(그냥 null 을 넣어준다)
// 네 번째 : 데이터 베이스 파일의 버전 번호
class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, "Test.db", null, 1){
    // 데이터 베이스 파일이 없으면 파일을 생성하고 이 메서드를 호출한다.
    // 개발자는 최신의 구조로 테이블이 만들어질 수 있도록 코드를 작성한다.
    // 어플리케이션을 새롭게 내려받을 사람들을 위한 메서드
    override fun onCreate(db: SQLiteDatabase?) {
        // 테이블의 구조를 정의하는 작업
        // create table TestTable : TestTable 이라는 이름의 테이블을 생성한다.
        // ( ) : 테이블의 내부 구조를 정의한다.
        // idx integer : idx 이라는 이름의 정수값을 담기 위한 컬럼
        // primary key : 중복을 허용하지 않고 null 값을 허용하지 않는다.
        // autoincrement : 1부터 1씩 증가되는 값이 자동으로 들어간다.
        // 각 행들을 구분하기 위한 값으로 사용한다.
        // textData text not null : 문자열을 저장하기 위한 textData 컬럼을 생성한다, null을 허용하지 않는다.
        // intData text not null : 정수값을 저장하기 위한 intData 컬럼을 생성한다. null을 허용하지 않는다.
        // doubleData real not null : 실수값을 저장하기 위한 doubleData 컬럼을 생성한다. null을 허용하지 않는다.
        // dateData date not null : 날짜 값을 저장하기 위한 dateData 컬럼을 생성한다. null을 허용하지 않는다.
        // 테이블의 구조를 정의하는 작업
        val sql = """
            create table TestTable(
                idx integer primary key autoincrement,
                textData text not null,
                intData integer not null,
                doubleData real not null,
                dateData date not null
            )
        """.trimIndent()

        db?.execSQL(sql)
    }

    // 이미 생성되어 있는 데이터베이스 파일의 버전 번호가 변경되면 호출되는 메서드
    // 어플리케이션을 기존에 내려받고 새 버전으로 업데이트를 한 사람들을 위한 메서드
    // 예전의 구조로 만들어져 있는 테이블들의 구조를 최신의 구조로 변경하는 작업을 작성한다.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}