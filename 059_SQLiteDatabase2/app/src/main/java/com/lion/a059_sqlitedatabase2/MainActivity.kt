package com.lion.a059_sqlitedatabase2

import android.content.ContentValues
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a059_sqlitedatabase2.databinding.ActivityMainBinding

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

        activityMainBinding.apply {

            button.setOnClickListener {
                // 데이터 베이스에 접근한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // ContentValues : 어떠한 컬럼에 어떠한 값을 사용하겠다고 지정할 수 있는 객체
                val cv1 = ContentValues()
                cv1.put("textData", "문자열1")
                cv1.put("intData", 100)
                cv1.put("doubleData", 11.11)
                cv1.put("dateData", "2024-11-08")

                val cv2 = ContentValues()
                cv2.put("textData", "문자열2")
                cv2.put("intData", 200)
                cv2.put("doubleData", 22.22)
                cv2.put("dateData", "2024-11-09")

                // 저장한다.
                // 첫 번째 : 데이터를 저장할 테이블의 이름
                // 두 번째 : null이 지정될 경우 null 값 대신 넣어줄 값. 그냥 null 넣어주세요
                // 세 번째 : ContentValues. ContentValues 에 저장할 때 사용한 이름이 컬럼명이되고
                // 지정된 값을 해당 컬럼에 저장해준다.
                sqLiteHelper.writableDatabase.insert("TestTable", null, cv1)
                sqLiteHelper.writableDatabase.insert("TestTable", null, cv2)

                sqLiteHelper.writableDatabase.close()

                textView.text = "저장 완료"
            }

            button2.setOnClickListener {
                textView.text = ""

                // 데이터 베이스에 접근한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 데이터를 가져온다.
                // 첫 번째 : 테이블의 이름
                // 두 번째 : 가지고 오고 싶은 컬럼의 이름 목록. null을 넣어주면 모든 컬럼의 데이터를 가져온다.
                // 세 번째 : 조건절. 없다면 null을 넣어준다.
                // 네 번째 : 조건걸의 ? 에 들어갈 값. 조건절이 없다면 null을 넣어준다.
                // 나머지는 순서대로 : group by, having, order by
                val cursor = sqLiteHelper.writableDatabase.query("TestTable", null, null, null, null, null, null)

                while(cursor.moveToNext()){
                    // 현재 행에서 데이터를 가져올 컬럼의 이름을 통해 순서값을 가져온다.
                    val idx1 = cursor.getColumnIndex("idx")
                    val idx2 = cursor.getColumnIndex("textData")
                    val idx3 = cursor.getColumnIndex("intData")
                    val idx4 = cursor.getColumnIndex("doubleData")
                    val idx5 = cursor.getColumnIndex("dateData")
                    // 순서값을 통해 데이터를 가져온다.
                    val idx = cursor.getInt(idx1)
                    val textData = cursor.getString(idx2)
                    val intData = cursor.getInt(idx3)
                    val doubleData = cursor.getDouble(idx4)
                    val dateData = cursor.getString(idx5)

                    textView.append("idx : ${idx}\n")
                    textView.append("textData : ${textData}\n")
                    textView.append("intData : ${intData}\n")
                    textView.append("doubleData : ${doubleData}\n")
                    textView.append("dateData : ${dateData}\n")
                    textView.append("\n")
                }
                // 데이터 베이스를 닫아준다.
                sqLiteHelper.writableDatabase.close()
            }

            button3.setOnClickListener {
                textView.text = ""

                // 데이터 베이스에 접근한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 데이터를 가져온다.
                // 세 번째 : 조건절
                // 네 번째 : 조건절의 ? 에 대입될 값을 문자열 배열로 지정해준다.
                val selection = "idx = ?"
                val selectionArgs = arrayOf("1")

                val cursor = sqLiteHelper.writableDatabase.query("TestTable", null, selection, selectionArgs, null, null, null)

                while(cursor.moveToNext()){
                    // 현재 행에서 데이터를 가져올 컬럼의 이름을 통해 순서값을 가져온다.
                    val idx1 = cursor.getColumnIndex("idx")
                    val idx2 = cursor.getColumnIndex("textData")
                    val idx3 = cursor.getColumnIndex("intData")
                    val idx4 = cursor.getColumnIndex("doubleData")
                    val idx5 = cursor.getColumnIndex("dateData")
                    // 순서값을 통해 데이터를 가져온다.
                    val idx = cursor.getInt(idx1)
                    val textData = cursor.getString(idx2)
                    val intData = cursor.getInt(idx3)
                    val doubleData = cursor.getDouble(idx4)
                    val dateData = cursor.getString(idx5)

                    textView.append("idx : ${idx}\n")
                    textView.append("textData : ${textData}\n")
                    textView.append("intData : ${intData}\n")
                    textView.append("doubleData : ${doubleData}\n")
                    textView.append("dateData : ${dateData}\n")
                    textView.append("\n")
                }
                // 데이터 베이스를 닫아준다.
                sqLiteHelper.writableDatabase.close()
            }

            button4.setOnClickListener {
                // 데이터 베이스에 접근한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 수정할 컬럼이름과 값을 담고 있는 ContentValues
                val cv1 = ContentValues()
                cv1.put("textData", "새로운 문자열")
                cv1.put("intData", 1000)

                // 조건절
                val where = "idx = ?"
                // 조건절의 ?에 들어갈 값
                val args = arrayOf("1")

                // 쿼리 실행
                // 첫 번째 : 테이블 이름
                // 두 번째 : 수정할 컬럼과 값의 데이터가 담긴 ContentValues
                // 세 번째 : 조건절
                // 네 번째 : 조건절의 ?에 들어갈 값
                sqLiteHelper.writableDatabase.update("TestTable", cv1, where, args)
                sqLiteHelper.writableDatabase.close()

                textView.text = "수정 완료"
            }

            button5.setOnClickListener {
                // 데이터 베이스에 접근한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 조건절
                val where = "idx = ?"
                // 조건절의 ?에 대입될 값
                val args = arrayOf("1")
                // 삭제한다.
                // 첫 번째 : 테이블 명
                // 두 번째 : 조건절. 조건에 만족하는 행을 삭제한다.
                // 세 번째 : 조건절의 ? 에 대입될 값
                sqLiteHelper.writableDatabase.delete("TestTable", where, args)
                sqLiteHelper.writableDatabase.close()

                textView.text = "삭제 완료"
            }
        }
    }
}