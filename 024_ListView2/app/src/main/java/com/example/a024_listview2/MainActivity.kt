package com.example.a024_listview2

import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a024_listview2.databinding.ActivityMainBinding

//여기세여ㅓ스는 ListView를 구성할 때 사용할 항목 View를 직접 만들어 사용한다.
// 항목 하나에 설정할 데이터가 문자열 하나인 경우

class MainActivity : AppCompatActivity() {

    // 리스트 뷰 구성을 위해 사용할 문자열 데이터
    val dataArray = arrayOf(
        "항목1", "항목2", "항목3", "항목4", "항목5",
        "항목6", "항목7", "항목8", "항목9", "항목10",
        "항목11", "항목12", "항목13", "항목14", "항목15",
        "항목16", "항목17", "항목18", "항목19", "항목20",
    )

    private lateinit var activityMainBinding: ActivityMainBinding
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

            // 두 번째 : 항목 하나의 View를 만들기 위한 layout 파일
            // 세 번째 : 문자열을 설정할 TextView 의 id
            listView1.adapter = ArrayAdapter<String>(
                this@MainActivity, R.layout.row, R.id.textViewRow, dataArray
            )
        }

    }
}