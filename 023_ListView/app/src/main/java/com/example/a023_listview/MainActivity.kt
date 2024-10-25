package com.example.a023_listview

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a023_listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    // 리스트 뷰 구성을 위해 사용할 문자열 데이터
    val dataArray = arrayOf(
        "항목1", "항목2", "항목3", "항목4", "항목5",
        "항목6", "항목7", "항목8", "항목9", "항목10",
        "항목11", "항목12", "항목13", "항목14", "항목15",
        "항목16", "항목17", "항목18", "항목19", "항목20",
    )
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
            // 항목 하나당 문자열 하나를 보여줄 수 있는 항목 View를 사용한다.
            // 항목의 개수 : 지정하는 문자열 배열(혹은 리스트)의 문자열 개수
            // 보여질 모양 : 안드로이드에서 제공하는 View를 사용한다.
            // 어떤 뷰에 어떤 데이터를 설정할 것인가 : android.R.id.text1 텍스트뷰에 문자열 하나가 설정된다.

            // 제공되는 어뎁터
            // dataArray에 있는 문자열의 개수 만큼 항목의 개수가 결정된다.
            // android.R.layout.simple_list_item_1을 가지고 항목 하나로 사용할 View를 만든다.
            // 만들어진 View가 가지고 있는 android.R.id.text1 이라는 TextView에 항목 번째 문자열을 넣어준다.
            val adapter1 = ArrayAdapter<String>(
                this@MainActivity, android.R.layout.simple_list_item_1, dataArray
            )
            // 어뎁터를 리스트뷰에 설정한다.
            listView1.adapter = adapter1

            // 리스트뷰의 항목을 누르면 동작하는 리스너
            // position : 사용자가 누른 항목의 순서값(0부터 1씩 증가)
            listView1.setOnItemClickListener { parent, view, position, id ->
                textView.text = dataArray[position]
            }

        }
    }
}