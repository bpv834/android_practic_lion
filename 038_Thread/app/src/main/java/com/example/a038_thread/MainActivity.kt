package com.example.a038_thread

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a038_thread.databinding.ActivityMainBinding
import kotlin.concurrent.thread

// 안드로이드 Thread
// Main Thread (UI Thread) : Activity가 실행되면 Android OS가 발생시키는 Thread
// 화면에 관련된 작업은 Main Thread에서 처리해야 한다.
// Main Thread에서 오래 걸리는 작업을 처리하게 되면 응답 없음 처리가 될 수도 있다
// User Thread : 개발자가 발생시키는 쓰래드. 오래 걸리는 작업이나 네트워크에 관련된 작업을 작성한다.
// User Thread에서는 화면에 관련된 작업을 할 수 없다.

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    // Activity 내부의 코드를 처리하는 Thread는 Main Thread이다.
    // Activity가 종료되면 Main Thread만 종료가 된다.
    // 이에 개발자가 발생시킨 User Thread는 계속 운영된다(Android OS의 판단하에 종료된다)
    // 만약 Activity가 종료될 때 User Thread도 종료되게 하고 싶다면 User Thread 내부의 코드가
    // 끝나도록 처리해줘야 한다.
    // User Thread내부의 반복문을 제어하기 위한 변수
    var isThreadRunning = false

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
                textView.text = " Button Click : ${System.currentTimeMillis()}"
            }
            // Activity의 코드가 끝나지 않아 화면관련 작업 처리 불가
            // 사용자 이벤트를 처리할 수도 없다
            // Activity에 만든 모든 메서드 내부의 코드는 빠르게 끝나야지만 화면처리를 할 수 있고
            // 사용자 이벤트에 대해서도 반응 가능
       /*     while (true) {
                // 100ms 만큼 쉰다.
                SystemClock.sleep(100)
                Log.d("test100", "while : ${System.currentTimeMillis()}")
            }*/
        }

        thread {
            isThreadRunning =true
            while (isThreadRunning){
                SystemClock.sleep(100)
                Log.d("test100","whilde : ${System.currentTimeMillis()}")
                // User Thread에 화면에 관련된 작업을 한다.
                // Android 8 부터는 화면에 관련된 작업은 모두 Main Thread가 처리하도록 구현돼 있다.
                // 즉 사용자 쓰레드에 화면에 관련된 작업을 하게되면 이 작업은 User Thread가 아닌
                // Main Thread에서 처리가 되도록 동작한다.
                // 하지만 하위 버전의 OS에는 오류가 발생하고 몇몇 처리를 할 때는 오류가 발생할 수 도 있다.
                // 이를 예방하기 위해서 runOnUiThread를 사용해야 한다.
                // runOnUiThread : 내부의 코드를 Main Thread가 처리하도록 하는 코드 블럭
                runOnUiThread {
                    activityMainBinding.textView2.text = "User Thread : ${System.currentTimeMillis()}"
                }
            }
        }
    }

    //Activity 가 소멸 될 때
    override fun onDestroy() {
        super.onDestroy()
        // User Thread내의 반복문이 종료될 수 있도록 변수에 false를 넣어준다.
        isThreadRunning = false
    }
}