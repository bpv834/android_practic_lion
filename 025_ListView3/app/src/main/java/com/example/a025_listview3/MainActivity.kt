package com.example.a025_listview3

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a025_listview3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    // 사용할 데이터
    // 이미지 리소스 아이디
    var imageArray = arrayOf(
        R.drawable.imgflag1,
        R.drawable.imgflag2,
        R.drawable.imgflag3,
        R.drawable.imgflag4,
        R.drawable.imgflag5,
        R.drawable.imgflag6,
        R.drawable.imgflag7,
        R.drawable.imgflag8
    )

    // 문자열1
    val strArray1 = arrayOf(
        "토고", "프랑스", "스위스", "스페인", "일본", "독일", "브라질", "대한민국"
    )

    // 문자열2
    val strArray2 = arrayOf(
        "togo", "france", "swiss", "spain", "japan", "german", "brazil", "korea"
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
            // SimpleAdapter
            // 사용할 layout 파일을 지정한다.
            // 어떤 뷰에 어떤 값을 넣을지를 지정할 수 있다.
            // 맵을 가지고 있는 리스트가 된다.
            // 맵 하나는 항목 하나를 구성하기 위해 필요한 데이터를 담아 준다.
            // 항목의 개수 만큼 맵을 생성하여 리스트에 담아준다.
            // 항목을 구성하기 위한 데이터를 담을 리스트를 구성한다.
            val dataList = mutableListOf<MutableMap<String, *>>()

            imageArray.forEachIndexed { index, i ->
                // 맵을 생성한다.
                val map = mutableMapOf(
                    "image" to imageArray[index],
                    "str1" to strArray1[index],
                    "str2" to strArray2[index]
                )
                // 리스트에 담아준다.
                dataList.add(map)

                // 데이터의 이름과 데이터를 담을 View의 아이디의 순서가 반드시 일치해야 한다.
                // 맵에 데이터를 담을 때 사용한 이름 배열
                val mapNameArray = arrayOf("image", "str1", "str2")
                // 데이터를 담을 뷰의 아이디 배열
                val viewIdArray = intArrayOf(R.id.imageViewRow, R.id.textViewRow1, R.id.textViewRow2)
                listView1.adapter = SimpleAdapter(
                    this@MainActivity, dataList, R.layout.row, mapNameArray , viewIdArray
                )
            }
        }
    }
}