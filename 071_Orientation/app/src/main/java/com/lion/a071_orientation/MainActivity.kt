package com.lion.a071_orientation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a071_orientation.databinding.ActivityMainBinding

// 화면 회전이 발생했을 경우 onCreate가 다시 호출된다.
// 이 때, 화면 회전 상태에 맞는 폴더의 xml 을 이용해 View들을 생성한다.
// 예를 들어 세로 모드 였다가 가로 모드로 회전했다고 가정한다.
// 일부 View에 있는 일부 속성이나 프로퍼티의 값이
// 그대로 유지되는 것들이 있다.
// 이는 id가 동일한 View에 대해 속성값이나 프로퍼티를 그대로 유지해준다.
// 허나 대부분은 유지하지 않는다.
// 이러한 처리를 위해서 savedInstanceState 를 사용한다.

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 화면 실행, 화면 회전 발생때 호출
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // viewBind 객체를 생성할 때 회전 상태에 다른 폴더를 선택해 그 폴더에 있는 xml 을 이용해
        //  화면 만든다
        // 세로모드 : layout-port
        // 가로모드 : layout-land
        // 기본 : layout
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        activityMainBinding.apply {

            // onCreadte 메서드의 매개변수인 savedInstanceState 는 acitivy가 실행될 때 null 이 들어온다.
            // 그리고 화면 회전시 bundle객체가 들어온다.
            // null이 아닐때만 처리
            if (savedInstanceState != null) {
                // 복원을 위해 필요한 데이터를 추출한다.
                val data1 = savedInstanceState.getString("data1")
                // view 설정
                textView.text = data1
            }

            button.setOnClickListener {
                textView.text = editTextText.text
            }
        }
    }
    // 화면 회전이 발생했을 때 호출되는 메서드
    // 매개변수로 Bundle 객체가 들어온다.
    // 이 객체 화면회전이 완료되고 onCreate 메서드가 다시 호출될 때 전달된다.
    // 이 객체에 회전 후 View에 설정하고 싶은 값들을 저장해둔다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 회전 후 복원을 위해 필요한 데이터들을 담아준다.
        outState.putString("data1", activityMainBinding.textView.text.toString())
    }
}