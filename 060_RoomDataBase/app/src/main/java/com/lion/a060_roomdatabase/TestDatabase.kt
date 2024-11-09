package com.lion.a060_roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// entities : RoomDatabase에서 사용할 모델 클래스들을 나열해준다.
// 나열된 모델 클래스에 작성되어 있는 테이블명을 보고 테이블을 만들어주고
// 모델 클래스가 가지고 있는 프로퍼티의 이름 및 자료형을 통해 테이블 내부의 구조를
// 정의한다(exportSchema = true)
@Database(entities = [DataModel::class], version = 1, exportSchema = true)
abstract class TestDatabase : RoomDatabase(){
    // dao
    abstract fun dataDao() : DataDao

    companion object{
        // 데이터 베이스 객체를 담을 변수
        var testDatabase:TestDatabase? = null
        // RoomDataBase는 접속부터 쿼리 실행까지 모두 별도의 쓰래드를 발생시켜서
        // 처리한다. 이 때문에 어떤 작업이 끝나지 않았음에도 불구하고 다른 작업이 동작하는
        // 문제가 발생할 수 있다. 이 때문에 순차적으로 작업이 진행될 수 있도록 하기위해
        // 동기화 처리를 해줘야 한다.
        @Synchronized
        fun getInstance(context: Context) : TestDatabase?{
            // 만약 데이터베이스 객체가 null이라면 객체를 생성한다.
            // Test.db : 데이터베이스 파일 이름
            synchronized(TestDatabase::class){
                testDatabase = Room.databaseBuilder(
                    context.applicationContext, TestDatabase::class.java,
                    "Test.db"
                ).build()
            }
            return testDatabase
        }

        // 데이터 베이스 객체가 소멸될 때 호출되는 메서드
        fun destroyInstance(){
            testDatabase = null
        }
    }
}