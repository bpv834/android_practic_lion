package com.lion.a067_resource1

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a067_resource1.databinding.ActivityMainBinding

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
            button.setOnClickListener{
                // 문자열을 직접 설정한다.
                // textView.text = "반갑니다"

                // 리소스로 등록되어 있는 문자열을 가져온다.
                // val str2 = resources.getString(R.string.str2)
                // textView.text = str2

                // 문자열을 자주 사용하는 요소이기 때문에 문자열을 가져올 수 있는 메서드를 따로 제공하고 있다.
                // Context가 가지고 있는 메서드
                // val str2 = getString(R.string.str2)
                // textView.text = str2

                // UI 요소들은 리소스 ID를 직접 지정하여 값을 가져올 수 있는 메서드들을 제공하고 있다.
                textView.setText(R.string.str2)
            }

            button2.setOnClickListener {
                // 문자열을 가지고 온다.
                val str3 = getString(R.string.str3)
                // 포멧 문자 부분에 설정될 값을 지정하여 문자열을 만들어준다.
                val str30 = String.format(str3, "홍길동", 30)

                textView.text = str30
            }

            button3.setOnClickListener {
                // boolean 값
                val bool1 = resources.getBoolean(R.bool.bool1)
                // int 값
                val int1 = resources.getInteger(R.integer.int1)

                textView.text = "bool1 : ${bool1}\n"
                textView.append("int1 : $int1")
            }

            button4.setOnClickListener {
                // string 배열
                val array1 = resources.getStringArray(R.array.str_array)
                // int 배열
                val array2 = resources.getIntArray(R.array.int_array)

                textView.text = ""

                array1.forEach {
                    textView.append("str : ${it}\n")
                }
                array2.forEach {
                    textView.append("int : ${it}\n")
                }
            }

            button5.setOnClickListener {
                // 사전 정의된 색상값 사용
                textView.setTextColor(Color.BLUE)

                // 사전 정의된 색상값 사용
                // textView.setTextColor(Color.BLUE)

                // RGB 지정(각 0 ~ 255)
                val c1 = Color.rgb(26, 106, 129)
                textView.setTextColor(c1)

                // ARGB 지정(A : alpha, 0 ~ 100)
                val c2 = Color.argb(50, 26, 106, 129)
                textView.setTextColor(c2)

                // Android 6.0 이후 부터는 Context.getColor() 사용을 권장하고 있다.
                val c4 = getColor(R.color.color1)
                textView.setTextColor(c4)
                textView.setBackgroundResource(R.color.color4)

            }

            button6.setOnClickListener {
//                dp -> 160ppi 액정에서 1dp = 1px
//                sp -> 160ppi 액정에서 1sp = 1px, 단말기에 설정된 글자크기 배율이 더해진다. 글자크기의 단위
//                px -> 픽셀
//                in -> 인치
//                mm -> 밀리미터
//                pt -> 1pt = 1/72 인치. 출판에서 사용하는 글자크기의 단위
                val px = resources.getDimension(R.dimen.dimen1)
                val dp = resources.getDimension(R.dimen.dimen2)
                val sp = resources.getDimension(R.dimen.dimen3)
                val inch = resources.getDimension(R.dimen.dimen4)
                val mm = resources.getDimension(R.dimen.dimen5)
                val pt = resources.getDimension(R.dimen.dimen6)

                textView.text = "1px : ${px}px\n"
                textView.append("1dp : ${dp}px\n")
                textView.append("1sp : ${sp}px\n")
                textView.append("1in : ${inch}px\n")
                textView.append("1mm : ${mm}px\n")
                textView.append("1pt : ${pt}px\n")

                textView.textSize = sp * 10
            }
        }
    }
}