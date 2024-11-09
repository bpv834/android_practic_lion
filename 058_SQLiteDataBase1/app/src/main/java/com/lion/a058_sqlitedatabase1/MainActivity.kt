package com.lion.a058_sqlitedatabase1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a058_sqlitedatabase1.databinding.ActivityMainBinding
// SQLite 데이터 베이스
// 모바일 어플리케이션을 개발할 때 사용할 수 있는 관계형 데이터 베이스
// 단말기 내부에 저장되기 때문에 어플 삭제시 같이 삭제된다.
// 임베디드 데이터 베이스로써 데이터베이스를 사용하는 소프트웨어 내부에 내장되는 데이터 베이스이다
// 안드로이드는 안드로이드 OS 내부에 설치되어 있다.
// 문법이 MySQL과 매우 유사한다.

// 구현 방법
// SQLiteOpenHelper를 구현해야 한다.
// SQLiteOpenHelper 는 데이터 데이터베이스 접속할 때 필요한 작업을 해주는 클래스이다.
// SQLiteOpenHeper를 통해 SQLite에 접속할 수 있으며 이를 통해 쿼리를 전달할 수 있다.
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
                // 쿼리문
                // 값이 들어가는 부분은 ? 로 작성해준다.
                // insert into TestTable : TestTable에 새로운 행을 추가한다.
                // (textData, intData, doubleData, dateData) : 데이터를 지정할 컬럼의 이름 목록
                // 지정되지 않는 컬럼에는 null값이 들어가며 autoincrement가 붙어있는 컬럼은
                // 1 부터 1씩 증가되는 값이 지정된다.
                // values (?, ?, ?, ?) : 위에서 지정한 컬럼에 들어갈 값들을 ( ) 안에 작성해준다.
                // SQLite 에서는 ?로 작성하고 ?에 들어갈 값을 나중에 지정한다.
                val sql = """
                    insert into TestTable
                    (textData, intData, doubleData, dateData)
                    values (?, ?, ?, ?)
                """.trimIndent()

                // ? 에 대입될 값들
                val arg1 = arrayOf("문자열1", 100, 11.11, "2024-11-08")
                val arg2 = arrayOf("문자열2", 200, 22.22, "2024-11-09")

                // 쿼리 실행
                sqLiteHelper.writableDatabase.execSQL(sql, arg1)
                sqLiteHelper.writableDatabase.execSQL(sql, arg2)

                // 데이터 베이스를 닫아준다.
                sqLiteHelper.writableDatabase.close()

                textView.text = "데이터 저장 완료"
            }
            button2.setOnClickListener {
                textView.text = ""
                // 데이터 베이스를 오픈한다.
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 쿼리문
                // TestTable에 있는 모든 데이터를 가져온다.
                val sql = "select * from TestTable"
                // 쿼리 실행
                // 첫 번째 : 수행할 쿼리문
                // 두 번째 : 쿼리문에 조건식에 들어갈 값(조건식이 없다면 null을 넣어준다)
                // 반환 : Cursor 객체
                // 쿼리문에 만족하는 데이터를 가져올 수 있는 객체
                val cursor = sqLiteHelper.writableDatabase.rawQuery(sql, null)
                // cursor는 처음에는 아무 행도 가르키지 않고 있다.
                // moveToNext 메서드를 호출하면 첫 번째 행을 가르키게 되고 데이터를 가져온다.
                // 또, moveToNext 메서를 사용하면 다음 행을 가르키게 되고 데이터를 가져온다.
                // moveToNext는 행 접근에 성공하면 true를 반환하고 접근에 실패하면 false를 반환한다.
                // 더이상 읽어올 행이 없는지는 false를 반환하는지 확인하면 된다.
                // 만약 가져올 행의 데이터가 0개 이상이라면 while문의 조건식으로 넣어준다.
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
                    textView.append("------------------------------------------\n")
                }
                // db 닫기
                sqLiteHelper.writableDatabase.close()
            }
            button3.setOnClickListener {
                textView.text = ""

                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                // 쿼리문
                // where idx = ? : idx 컬럼의 값이 ?(나중에 값을 대입)와 같은 행만 가져온다.
                val sql = """
                    select * from TestTable
                    where idx = ?
                """.trimIndent()

                // 쿼리 실행
                // 두번째 : 쿼리문의 ? 에 대입될 값을 지정한다. 문자열 배열로 넣어줘야 한다.
                val arg1 = arrayOf("1")
                val cursor = sqLiteHelper.writableDatabase.rawQuery(sql, arg1)

                while(cursor.moveToNext()){

                    val idx1 = cursor.getColumnIndex("idx")
                    val idx2 = cursor.getColumnIndex("textData")
                    val idx3 = cursor.getColumnIndex("intData")
                    val idx4 = cursor.getColumnIndex("doubleData")
                    val idx5 = cursor.getColumnIndex("dateData")

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
                // 데이터 베이스 접속
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                //  쿼리문
                // update TestTable  : TestTable을 수정할거야~
                // set textData = ?, intData = ? : textData 컬럼은 ?로, intData 컬럼은 ? 로 값을 덮어씌워줘
                // where idx = ? : idx가 ? 인 행의 데이터를 수정해줘~
                val sql = """
                    update TestTable
                    set textData = ?, intData = ?
                    where idx = ?
                """.trimIndent()

                // ? 에 대입될 값들
                val arg1 = arrayOf("새로운 문자열", 1000, 1)
                // 쿼리 실행
                sqLiteHelper.writableDatabase.execSQL(sql, arg1)
                sqLiteHelper.writableDatabase.close()

                textView.text = "수정 완료"
            }
            button5.setOnClickListener {
                // 데이터 베이스 접속
                val sqLiteHelper = SQLiteHelper(this@MainActivity)
                //  쿼리문
                // delete from TestTable : TestTable의 행을 삭제할거야~
                // where idx = ? : idx 컬럼이 ? 인 행을 삭제해줘~
                val sql = """
                    delete from TestTable
                    where idx = ?
                """.trimIndent()

                // ? 에 대입될 값들
                val arg1 = arrayOf(1)
                // 쿼리 실행
                sqLiteHelper.writableDatabase.execSQL(sql, arg1)
                sqLiteHelper.writableDatabase.close()

                textView.text = "삭제 완료"
            }
        }
    }
}