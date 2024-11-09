package com.lion.a060_roomdatabase

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a060_roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

// RoomDatabase
// SQLiteDataBase를 보다 쉽고 편하게 사용할 수 있도록 제공되는 라이브러리
// 그렇다고 하더라도 SQL 쿼리문에 대한 지식은 필요하다

// 라이브러리 설정 방법
// build.gradle.kts(Module:app)

// 1. plugins 설정
// kotlin("kapt") 를 설정한다.
// 이 것은 코틀린 주석 처리기라고 부른다. 어노테이션(@머머머)에 대한 처리를 하는 도구이다.
//    plugins {
//          ...
//        kotlin("kapt")
//    }

// 2. 라이브러리 추가
//    dependencies {
//            ...
//
//        implementation("androidx.room:room-runtime:2.6.1")
//        annotationProcessor("androidx.room:room-compiler:2.6.1")
//        kapt("androidx.room:room-compiler:2.6.1")
//    }

// 작업 순서
// 1. 테이블의 구조와 동일한 구조를 가지고 있는 클래스를 작성해준다.
// 여기에서는 DataModel 클래스를 사용한다.

// 2. Dao Interface를 작성해준다.
// 작성한 Interface를 구현한 클래스가 자동으로 생성되며 그 클래스에서 구현한
// 메서드들은 앞서 살펴본 SQLiteDataBase를 사용하는 코드로 자동 구현된다.
// 여기에서는 DataDao 인터페이스를 사용한다.

// 3. 데이터 베이스 객체를 생성한다.
// 데이터 베이스를 관리하고 각종 쿼리문 등을 관리하는 객체이다
// 여기에서는 TestDatabase 클래스를 사용한다.

// 4. 필요한 곳에서 사용한다.

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // RoomDataBase 는 내부적으로 쓰래드를 발생시켜서 처리한다.
        // 이제 데이터 베이스에 관련된 작업이 모두 끝난다음 다음 작업이 이루어질수 있도록 하기 위해
        // 동기화 처리 해야 한다. 여기에서는 코루틴을 사용한다.
        activityMainBinding.apply {
            button.setOnClickListener {
                // 데이터 베이스 객체를 가져온다.
                val testDatabase = TestDatabase.getInstance(this@MainActivity)
                // 데이터를 담을 객체를 생성한다.
                // autoGenerate 가 붙어있는 프로퍼티는 제외한다.
                val dataModel1 = DataModel(textData = "문자열1", intData = 100, doubleData = 11.11)
                val dataModel2 = DataModel(textData = "문자열2", intData = 200, doubleData = 22.22)

                CoroutineScope(Dispatchers.Main).launch {
                    // IO 코루틴을 동작시킨다. 이때 동기화한다.
                    async(Dispatchers.IO){
                        // 데이터를 저장한다.
                        testDatabase?.dataDao()?.insertData(dataModel1)
                    }
                }

                CoroutineScope(Dispatchers.Main).launch {
                    async(Dispatchers.IO){
                        testDatabase?.dataDao()?.insertData(dataModel2)
                    }
                }

                textView.text = "저장 완료"
            }

            button2.setOnClickListener {
                textView.text = ""

                // 데이터 베이스 객체를 가져온다.
                val testDataBase = TestDatabase.getInstance(this@MainActivity)

                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        // 데이터를 가져온다.
                        testDataBase?.dataDao()?.selectDataAll()
                    }
                    // 코투린에서 await 메서드를 사용하면
                    // 작업이 끝날 때까지 대기하고 있다가 반환하는 값을 받을 수 있다.
                    val dataList = work1.await() as List<DataModel>

                    // 출력한다.
                    dataList.forEach {
                        textView.append("idx : ${it.idx}\n")
                        textView.append("textData : ${it.textData}\n")
                        textView.append("intData : ${it.intData}\n")
                        textView.append("doubleData : ${it.doubleData}\n\n")
                    }
                }
            }

            button3.setOnClickListener {
                val testDatabase = TestDatabase.getInstance(this@MainActivity)
                CoroutineScope(Dispatchers.Main).launch {
                    val work1 = async(Dispatchers.IO){
                        testDatabase?.dataDao()?.selectDataOne(1)
                    }
                    val dataModel = work1.await() as DataModel

                    textView.text = "idx : ${dataModel.idx}\n"
                    textView.append("textData : ${dataModel.textData}\n")
                    textView.append("intData : ${dataModel.intData}\n")
                    textView.append("doubleData : ${dataModel.doubleData}")
                }
            }

            button4.setOnClickListener {
                val testDatabase = TestDatabase.getInstance(this@MainActivity)
                // 주의
                // autoGenerate 가 붙어져 있는 컬럼이 조건절에 들어간다.
                // 나머지 프로퍼티에 담겨져 있는 데이터 모두가 컬럼에 반영된다.
                // 따라서 일부만 바꾸고 싶다고 하더라도 모든 프로프티에 값을 반드시 담아줘야 한다.
                val dataModel = DataModel(1, "새로운 문자열", 1000, 1000.1111)
                // 수정
                CoroutineScope(Dispatchers.Main).launch {
                    async(Dispatchers.IO){
                        testDatabase?.dataDao()?.updateData(dataModel)
                    }
                    textView.text = "수정 완료"
                }
            }

            button5.setOnClickListener {
                val testDatabase = TestDatabase.getInstance(this@MainActivity)
                // autoGenerate 가 붙어있는 프로퍼티의 값이 조건절로 들어간다.
                // 삭제는 값을 넣는게 아니기 때문에 autoGenerate 프로퍼티에 값만 넣어주면 된다.
                val dataModel = DataModel(idx = 1)

                CoroutineScope(Dispatchers.Main).launch {
                    async(Dispatchers.IO){
                        testDatabase?.dataDao()?.deleteData(dataModel)
                    }
                    textView.text = "삭제 완료"
                }
            }
        }
    }
}