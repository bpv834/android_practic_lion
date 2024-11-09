package com.lion.a061_ex_roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// entities는 데이터베이스에 포함된 엔티티들(테이블)을 지정하고, version은 데이터베이스의 버전을 관리하며, exportSchema는 스키마 파일을 내보내는 설정을 담당합니다.
@Database(entities = [DataModel :: class], version = 1, exportSchema = true)
abstract class StudentDataBase : RoomDatabase() {
    // dao 내가 구현하지 않아도, Room 클래스가 우리 dao 에 맞게 구현한 메서드를 오버라이딩 하여 제공하는 것이다, 그래서 구현할 필요가 없다.
    abstract fun dataDao() : DataDao

    // 질문, getInstance(context: Context)의 반환 타입은 StudentDataBase?{} 가 리턴하는 타입을 반환하는 것인가?, 널이면 객체를 생성해 반환하는 것이면, 널러블타입을 리턴하는게 맞는가?
    companion object{
        // 데이터 베이스 객체를 담을 변수
        // 싱글턴 객체
        var studentDatabase:StudentDataBase? = null
        @Synchronized // @Synchronized 어노테이션은 메서드를 한 번에 하나의 스레드만 접근할 수 있도록 보장하고,
        // 멀티스레드 환경에서 여러 스레드가 동시에 데이터베이스 객체를 생성하려는 경우를 방지합니다.
        fun getInstance(context: Context) : StudentDataBase?{
            // 만약 데이터베이스 객체가 null이라면 객체를 생성한다. 널체크는 어디서 했는가?
            synchronized(StudentDataBase::class){ // synchronized(StudentDataBase::class)는 클래스 전체를 잠그는 역할을 하여,
                studentDatabase = Room.databaseBuilder(
                    context.applicationContext, StudentDataBase::class.java,
                    "Student.db" // Room.databaseBuilder() 메서드를 사용하여 StudentDataBase의 인스턴스를 생성합니다
                ).build()
            }
            return studentDatabase
        }

        // 데이터 베이스 객체가 소멸될 때 호출되는 메서드
        fun destroyInstance(){
            studentDatabase = null
        }
    }

}