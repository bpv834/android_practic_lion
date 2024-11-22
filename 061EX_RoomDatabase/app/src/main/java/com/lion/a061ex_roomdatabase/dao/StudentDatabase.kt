package com.lion.a061ex_roomdatabase.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lion.a061ex_roomdatabase.vo.StudentVO

// 복붙 후...
// entities의 VO 클래스 이름 변경 혹은 추가
// 클래스 이름 변경
// dao를 반환하는 메서드의 이름과 반환 타입 변경
// companion object 내의 데이터 베이스 변수명과 타입 변경
// 빨간색으로 떠있는 변수의 이름과 클래스 타입을 변경
// 데이터 베이스 파일 이름 변경
@Database(entities = [StudentVO::class], version = 1, exportSchema = true)
abstract class StudentDatabase : RoomDatabase(){
    abstract fun studentDAO() : StudentDAO

    companion object{
        var studentDatabase:StudentDatabase? = null
        @Synchronized
        fun getInstance(context: Context) : StudentDatabase?{
            synchronized(StudentDatabase::class){
                studentDatabase = Room.databaseBuilder(
                    context.applicationContext, StudentDatabase::class.java,
                    "Student.db"
                ).build()
            }
            return studentDatabase
        }

        fun destroyInstance(){
            studentDatabase = null
        }
    }
}