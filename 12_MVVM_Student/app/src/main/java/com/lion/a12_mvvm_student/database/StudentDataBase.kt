package com.lion.a12_mvvm_student.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lion.a12_mvvm_student.dao.StudentDao
import com.lion.a12_mvvm_student.vo.StudentVO

@Database(entities = [StudentVO::class], version = 1, exportSchema = true)
abstract class StudentDataBase : RoomDatabase() {
    // dao
    abstract fun studentDao(): StudentDao

    companion object{
        // 데이터 베이스 파일 이름
        val dataBaseName = "Student.db"
        // 데이터 베이스 객체를 담을 변수
        var studentDatabase:StudentDataBase? = null
        @Synchronized
        fun getInstance(context: Context) : StudentDataBase?{
            // 만약 데이터베이스 객체가 null이라면 객체를 생성한다.
            synchronized(StudentDataBase::class){
                studentDatabase = Room.databaseBuilder(
                    context.applicationContext, StudentDataBase::class.java,
                    dataBaseName
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