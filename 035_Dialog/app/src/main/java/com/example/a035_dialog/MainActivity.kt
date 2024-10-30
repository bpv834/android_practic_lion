package com.example.a035_dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a035_dialog.databinding.ActivityMainBinding
import com.example.a035_dialog.databinding.CustomDialogBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

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
                // 다이얼로그를 구성한다.
                val builder1 = MaterialAlertDialogBuilder(this@MainActivity)
                // 타이틀
                builder1.setTitle("기본 다이얼로그")
                // 메시지
                builder1.setMessage("기본 다이얼로그 입니다")
                // 아이콘
                builder1.setIcon(R.mipmap.ic_launcher)
                // 버튼을 배치 (총 3개를 배치할 수 있다)
                // 버튼을 누르면 다이얼로그가 사라지는 것은 기본적으로 된다.
                // 다이얼로그가 사라지면 되는 버튼은 리스너를 null을 설정한다
                builder1.setPositiveButton("Positive"){ dialogInterface: DialogInterface, i: Int ->
                    textView.text = "Positive 버튼을 눌렀습니다"
                }
                builder1.setNeutralButton("Neutral", null)
                builder1.setNegativeButton("Negative"){ dialogInterface: DialogInterface, i: Int ->
                    textView.text = "Negative 버튼을 눌렀습니다"
                }
                // 다이얼로그를 띄운다.
                builder1.show()
            }
            button2.setOnClickListener{
                val builder1 = MaterialAlertDialogBuilder(this@MainActivity)
                builder1.setTitle("커스텀 다이얼로그 다이얼로그")
                // 다이얼로그를 커스터마이징 할 때는 지정한 View가 Message 위치에 나오므로
                // Message를 설정하지 않는다.
                // builder1.setMessage("기본 다이얼로그 입니다")
                builder1.setIcon(R.mipmap.ic_launcher)

                //설정할 View에 대한 작업을 한다.
                val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
                customDialogBinding.editTextDialog1.setText("")
                customDialogBinding.editTextDialog2.setText("")

                // View를 Dialog에 설정해준다.
                builder1.setView(customDialogBinding.root)

                builder1.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    textView.text = "${customDialogBinding.editTextDialog1.text}\n"
                    textView.append("${customDialogBinding.editTextDialog2.text}")
                }
                builder1.setNegativeButton("취소", null)

                builder1.show()
            }
            button3.setOnClickListener {

            }
        }
    }
}