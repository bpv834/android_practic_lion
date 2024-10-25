package com.example.a025ex_listview

import android.os.Bundle
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a025ex_listview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
            val dataList = mutableListOf<MutableMap<String, *>>()

            imageArray.forEachIndexed { index, i ->
                val map = mutableMapOf(
                    "image" to imageArray[index],
                    "str1" to strArray1[index]
                )

                dataList.add(map)

                val mapNameArray = arrayOf("image", "str1")
                val viewIdArray = intArrayOf(R.id.imgViewRow, R.id.txtViewRow)
                listView.adapter = SimpleAdapter(
                    this@MainActivity, dataList, R.layout.row, mapNameArray, viewIdArray
                )
            }

            listView.setOnItemClickListener { parent, view, position, id ->
                val selectedImage = imageArray[position]
                mainImgView.setImageResource(selectedImage)

                val selectedText = strArray1[position]
                mainTxtView.text = selectedText
            }
        }
    }
}