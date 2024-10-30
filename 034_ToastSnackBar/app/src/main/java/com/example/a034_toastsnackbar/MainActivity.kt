package com.example.a034_toastsnackbar

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a034_toastsnackbar.databinding.ActivityMainBinding
import com.example.a034_toastsnackbar.databinding.ToastBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 스낵바 닫기를 테스트하기 위해 프로퍼티를 정의한다.
    lateinit var snackbar1:Snackbar

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
                // Toast 메시지를 요청한다.
                // Toast 메시지는 안드로이드 OS 에게 요청하는 메시지이다.
                // 어플 실행 여부에 관계없이 요청 받은 순서대로 메시지를 보여주며 일정 시간이 지나면 자동으로 사라진다.
                // 사용자가 보지 못할 수 있는 메시지이다.
                // 두 번째 : 보여주고 싶은 메시지
                // 세 번째 : 메시지를 보여줄 시간 (LENGTH_SHORT, LENGTH_LONG)
                val t1 = Toast.makeText(this@MainActivity, "기본 토스트", Toast.LENGTH_SHORT)
                // 메시지 출력을 OS에게 요청한다.
                t1.show()
            }

            button2.setOnClickListener {
                val toastBinding = ToastBinding.inflate(layoutInflater)
                toastBinding.textViewToast.text = "안녕하세요"

                val t2 = Toast(this@MainActivity)
                // View를 설정해준다.
                t2.view = toastBinding.root
                // 메시지가 표시될 화면상의 위치를 설정한다.
                t2.setGravity(Gravity.CENTER, 0, -100)
                // 표시될 시간
                t2.duration = Toast.LENGTH_SHORT
                // 메시지를 보여준다.
                t2.show()
            }

            // SnackBar : 화면 하단에 나타나는 메시지
            // 일정 시간이 지나면 사라지게 할 수도 있고 계속 유지되어 있게 할 수도 있다.
            button3.setOnClickListener {
                // SnackBar 객체를 생성한다.
                // 마지막은 메시지가 보여주는 시간. LENGTH_INDEFINITE로 설정하면 사라지지 않는다.
                // 코드를 통해 사라지게 해야 한다.
                // val snackbar1 = Snackbar.make(root, "기본 스낵바", Snackbar.LENGTH_SHORT)
                snackbar1 = Snackbar.make(root, "기본 스낵바", Snackbar.LENGTH_INDEFINITE)

                // 메시지 색상
                snackbar1.setTextColor(Color.RED)
                // 배경색
                snackbar1.setBackgroundTint(Color.BLUE)

                snackbar1.setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
                // Action 설정
                snackbar1.setAction("Action"){
                    textView.text = "SnackBar의 Action을 눌렀습니다"
                }

                // Callback 설정
                snackbar1.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>(){
                    override fun onShown(transientBottomBar: Snackbar?) {
                        super.onShown(transientBottomBar)
                        textView.text = "SnackBar 메시지가 나타났습니다"
                    }

                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        textView.text = "SnackBar 메시지가 사라졌습니다"
                    }
                })

                // SnackBar를 표시한다.
                snackbar1.show()
            }
            button4.setOnClickListener {
                // snackbar1 변수가 초기화 되어 있다면...
                if(::snackbar1.isInitialized) {
                    // 스낵바가 보여지고 있는 상태라면..
                    if (snackbar1.isShown == true) {
                        // 스낵바를 사라지게 한다.
                        snackbar1.dismiss()
                    }
                }
            }


        }
    }
}